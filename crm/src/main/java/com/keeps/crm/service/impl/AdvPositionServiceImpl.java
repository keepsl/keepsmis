package com.keeps.crm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.crm.dao.AdvDao;
import com.keeps.crm.dao.AdvPositionDao;
import com.keeps.crm.service.AdvPositionService;
import com.keeps.crm.utils.CreateAdvCacheFile;
import com.keeps.crm.utils.aliyunoss.AliyunOSSClientUtil;
import com.keeps.model.TAdv;
import com.keeps.model.TAdvPosition;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.Constants;

/** 
 * <p>Title: AdvPositionServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class AdvPositionServiceImpl extends AbstractService implements AdvPositionService {

	@Autowired
	private AdvPositionDao advPositionDao;
	@Autowired
	private AdvDao advDao;

	@Override
	public Page queryList(TAdvPosition advPosition) {
		return advPositionDao.queryList(advPosition);
	}

	@Override
	public List<TAdvPosition> queryAll() {
		return advPositionDao.queryAll();
	}

	@Override
	public TAdvPosition getById(Integer id) {
		TAdvPosition advPosition = super.get(TAdvPosition.class, id);
		return advPosition;
	}

	@Override
	public String saveOrUpdate(TAdvPosition advPosition, MultipartFile defaultcontentfile, HttpServletRequest request) {
		Assert.isTrue(StringUtils.hasText(advPosition.getApName()), "名称不能为空!");
		Assert.isTrue(advPosition.getApClass()!=null, "类别不能为空!");
		Assert.isTrue(advPosition.getApDisplay()!=null, "展示方式不能为空!");
		Assert.isTrue(advPosition.getIsShow()!=null, "是否展示不能为空!");
		if (advPosition.getApClass()==2) {//文字广告位
			Assert.isTrue(advPosition.getApWidthWord()!=null, "字数不能为空!");
			Assert.isTrue(StringUtils.hasText(advPosition.getDefaultContentWord()), "默认文字不能为空!");
			advPosition.setApWidth(advPosition.getApWidthWord());
			advPosition.setDefaultContent(advPosition.getDefaultContentWord());
		}else{
			Assert.isTrue(advPosition.getApWidth()!=null, "宽度不能为空!");
			Assert.isTrue(advPosition.getApHeight()!=null, "高度不能为空!");
			Assert.isTrue(advPosition.getApWidth()>=10, "宽度不能小于10!");
			Assert.isTrue(advPosition.getApWidth()>=10, "高度不能小于10!");
			if (defaultcontentfile==null) {//修改时可空
				//修改时上传的图片为空，但是原图片ID必须存在
				if (StringUtils.notText(advPosition.getDefaultContent())) {
					throw new CapecException("默认封面不能为空!");
				}
			}else{
				/*String uploadpath = Constants.file_upload_path+File.separator+Constants.ADV_POSITION_IMAGE_PATH;
				String originalFilename = defaultcontentfile.getOriginalFilename();
				String filetype = originalFilename.substring(originalFilename.lastIndexOf(StringUtils.Symbol.POINT)+1);
				if(!CommonUtils.isExistStr(filetype.toLowerCase(), new String[]{"jpg","bmp","gif","png"}))
					throw new CapecException("不允许上传" + filetype + "格式的文件！");
				String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(StringUtils.Symbol.POINT), originalFilename.length());
				File targetFile = new File(uploadpath, filename);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					defaultcontentfile.transferTo(targetFile);
				} catch (Exception e) {
					log.error(e);
					throw new CapecException("文件上传失败!");
				}*/
				/** start **/
				String origfilename = defaultcontentfile.getOriginalFilename();
				String filetype = origfilename.substring(origfilename.lastIndexOf(StringUtils.Symbol.POINT)+1);
				if(!CommonUtils.isExistStr(filetype.toLowerCase(), new String[]{"jpg","bmp","gif","png"}))
					throw new CapecException("不允许上传" + filetype + "格式的文件！");
				String filename = UUID.randomUUID() +"."+ filetype;
				Assert.isTrue(defaultcontentfile.getSize() / (1024 * 1024) <= Constants.MAX_UPLOAD_IMAGE_SIZE,"上传的图片大小请控制在" + Constants.MAX_UPLOAD_IMAGE_SIZE + "M内!");
				try {
					AliyunOSSClientUtil.uploadObject2OSS(defaultcontentfile.getInputStream(), Constants.ADV_POSITION_IMAGE_PATH, filename);
				} catch (IOException e) {
					log.error(e);
					throw new CapecException("文件上传失败!");
				}
				/** end **/
				//ImageUtils.cut4(targetFile.getPath(), uploadpath + File.separator + "cut"+filename,Constants.ADV_POSITION_CUT_IMAGE_WIDTH_HEIGHT.get(1), Constants.ADV_POSITION_CUT_IMAGE_WIDTH_HEIGHT.get(2));
				//原图存在，删除原图片
				if (StringUtils.hasText(advPosition.getDefaultContent())) {
					AliyunOSSClientUtil.deleteFile(Constants.ADV_POSITION_IMAGE_PATH, advPosition.getDefaultContent());
					/*File file = new File(uploadpath, advPosition.getDefaultContent());
					file.delete();
					
					file = new File(uploadpath, "cut"+advPosition.getDefaultContent());
					file.delete();*/
				}
				advPosition.setDefaultContent(filename);
			}
		}
		if (advPosition.getId()==null) {//保存赋默认值
			advPosition.setAdvNum(0);
			advPosition.setClickNum(0);
			advPosition.setApPrice(0F);
			advPosition.setIsCache(0);//未缓存
		}
		super.saveOrUpdateEntity(advPosition, EditType.NULL_UN_UPDATE);
		return null;
	}

	@Override
	public String updateFieidById(String fieid, Integer value, String ids) {
		advPositionDao.updateFieidById(fieid, value, ids);
		return "更新成功!";
	}
	
	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		String[] idarr = ids.split(",");
		for (String id : idarr) {
			Integer countnum = advDao.getCountByApId(Integer.parseInt(id));
			if (countnum>0) {
				throw new CapecException("广告位下已经存在广告,不允许删除.");
			}
		}
		super.removeEntity(TAdvPosition.class, ids, IdTypes.Integer);
		return "删除成功!";
	}

	public String cacheAdvFile(String ids){
		Assert.isTrue(StringUtils.hasText(ids), "请选择要缓存的广告位的数据.");
		String[] idarr = ids.split(",");
		List<TAdvPosition> listAdvPosition = new ArrayList<>();
		for (String id : idarr) {
			int iid = Integer.parseInt(id);
			TAdvPosition upadvPosition = new TAdvPosition();
			upadvPosition.setIsCache(1);
			upadvPosition.setId(iid);
			listAdvPosition.add(upadvPosition);
			List<TAdv> listadv = advDao.getAdvByApId(iid);
			TAdvPosition advPosition = super.get(TAdvPosition.class, iid);
			String filename = "layout_adv_";
			if (listadv.size()==0) {//没有广告，把广告位生成默认的
				filename += iid+".js";
				switch (advPosition.getApClass()) {
				case 1://广告位类别是图片
					CreateAdvCacheFile.createManyImageAdvPosition(filename, advPosition);
					break;
				case 2://广告位类别是文字
					break;
				case 3://广告位类别是幻灯
					CreateAdvCacheFile.createSlideshowAdvPosition(filename,advPosition);
					break;
				case 4://广告位类别是Flash
					break;
				}
			}else{
				filename += iid+".js";
				switch (advPosition.getApClass()) {
				case 1://广告位类别是图片
					CreateAdvCacheFile.createManyImageAdv(filename, listadv,advPosition.getApWidth(),advPosition.getApHeight());
					break;
				case 2://广告位类别是文字
					break;
				case 3://广告位类别是幻灯
					CreateAdvCacheFile.createSlideshowAdv(filename,listadv,advPosition.getApWidth(),advPosition.getApHeight());
					break;
				case 4://广告位类别是Flash
					break;
				}
			}
		}
		super.saveOrUpdateAllEntity(listAdvPosition, EditType.NULL_UN_UPDATE);
		return null;
	}

}
