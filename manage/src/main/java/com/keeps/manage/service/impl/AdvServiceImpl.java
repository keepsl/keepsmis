package com.keeps.manage.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.AdvDao;
import com.keeps.manage.service.AdvService;
import com.keeps.model.TAdv;
import com.keeps.model.TAdvPosition;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.ImageUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.Constants;

/** 
 * <p>Title: AdvServiceImpl.java</p>  
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
public class AdvServiceImpl extends AbstractService implements AdvService {

	@Autowired
	private AdvDao advDao;

	@Override
	public Page queryList(TAdv adv) {
		Page page = advDao.queryList(adv);
		return page;
	}

	@Override
	public List<TAdv> queryAll() {
		return advDao.queryAll();
	}

	@Override
	public TAdv getById(Integer id) {
		return super.get(TAdv.class, id);
	}

	@Override
	public String saveOrUpdate(TAdv adv, MultipartFile advcontentfile, HttpServletRequest request) {
		Assert.isTrue(StringUtils.hasText(adv.getAdvTitle()), "广告名称不能为空!");
		Assert.isTrue(StringUtils.hasText(adv.getAdvLink()), "广告连接不能为空!");
		Assert.isTrue(adv.getApId()!=null && adv.getApId() !=0, "所属广告位不能为空!");
		if (adv.getId()==null) {
			if (adv.getApDisplay()==3) {
				Integer countnum = advDao.getCountByApId(adv.getApId());
				if (countnum>0) {
					throw new CapecException("选择的广告位是单广告展示,广告记录已经存在!");
				}
			}
		}
		Assert.isTrue(StringUtils.hasText(adv.getStarttime()), "开始时间不能为空!");
		Assert.isTrue(StringUtils.hasText(adv.getEndtime()), "结束时间不能为空!");
		Assert.isTrue(StringUtils.hasText(adv.getAdvTitle()), "标签名称不能为空!");
		Assert.isTrue(adv.getSlideSort()!=null, "排序不能为空!");
		Assert.isTrue(adv.getIsShow()!=null, "是否展示不能为空!");
		if (adv.getApClass()==2) {//文字广告位
			Assert.isTrue(StringUtils.hasText(adv.getAdvContent()), "文字内容不能为空!");
		}else{
			if (advcontentfile==null) {//修改时可空
				//修改时上传的图片为空，但是原图片ID必须存在
				if (StringUtils.notText(adv.getAdvContentpath())) {
					throw new CapecException("图片/Flash不能为空!");
				}
			}else{
				String uploadpath = Constants.file_upload_path+File.separator+Constants.ADV_IMAGE_PATH;
				String originalFilename = advcontentfile.getOriginalFilename();
				String filetype = originalFilename.substring(originalFilename.lastIndexOf(StringUtils.Symbol.POINT)+1);
				if(!CommonUtils.isExistStr(filetype.toLowerCase(), new String[]{"jpg","bmp","gif","png"}))
					throw new CapecException("不允许上传" + filetype + "格式的文件！");
				String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(StringUtils.Symbol.POINT), originalFilename.length());
				File targetFile = new File(uploadpath, filename);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					advcontentfile.transferTo(targetFile);
				} catch (Exception e) {
					log.error(e);
					throw new CapecException("文件上传失败!");
				}
				Assert.isTrue(targetFile.length() / (1024 * 1024) <= Constants.MAX_UPLOAD_IMAGE_SIZE,"上传的图片大小请控制在" + Constants.MAX_UPLOAD_IMAGE_SIZE + "M内!");
				ImageUtils.cut4(targetFile.getPath(), uploadpath + File.separator + "cut"+filename,Constants.ADV_CUT_IMAGE_WIDTH_HEIGHT.get(1), Constants.ADV_CUT_IMAGE_WIDTH_HEIGHT.get(2));
				//原图存在，删除原图片
				if (StringUtils.hasText(adv.getAdvContentpath())) {
					File file = new File(uploadpath, adv.getAdvContentpath());
					file.delete();
					
					file = new File(uploadpath, "cut"+adv.getAdvContentpath());
					file.delete();
				}
				adv.setAdvContent(filename);
			}
		}
		TAdvPosition advPosition = new TAdvPosition();
		if (adv.getId()==null) {//更新广告位广告数
			TAdvPosition oAdvPosition = super.get(TAdvPosition.class, adv.getApId());
			advPosition.setAdvNum(oAdvPosition.getAdvNum()+1);
		}
		advPosition.setId(adv.getApId());
		advPosition.setIsCache(2);
		super.update(advPosition, EditType.NULL_UN_UPDATE);
		adv.setClickNum(0);
		adv.setAdvPrice(0F);
		super.saveOrUpdateEntity(adv, EditType.NULL_UN_UPDATE);
		return null;
	}

	@Override
	public String updateFieidById(String fieid, Integer value, String ids) {
		advDao.updateFieidById(fieid, value, ids);
		return "更新成功!";
	}
	
	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		List<TAdv> list = advDao.getGroupApidListByIds(ids);
		List<TAdvPosition> listAdvPosition = new ArrayList<>();
		for (TAdv tAdv : list) {
			TAdvPosition advPosition = new TAdvPosition();
			advPosition.setAdvNum(tAdv.getAdvNum()-tAdv.getCountnum());
			advPosition.setId(tAdv.getApId());
			listAdvPosition.add(advPosition);
		}
		super.saveOrUpdateAllEntity(listAdvPosition, EditType.NULL_UN_UPDATE);
		super.removeEntity(TAdv.class, ids, IdTypes.Integer);
		return "删除成功!";
	}

}
