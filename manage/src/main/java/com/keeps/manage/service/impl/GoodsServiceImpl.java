package com.keeps.manage.service.impl;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.GoodsDao;
import com.keeps.manage.service.GoodsService;
import com.keeps.utils.Constants;
import com.keeps.utils.ImageUtil;
import com.keeps.utils.ValidateUtil;
import com.keeps.model.SzlGoods;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsServiceImpl.java</p>  
 * <p>Description: 商品Service实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class GoodsServiceImpl extends AbstractService implements GoodsService {
	
	@Autowired
	private GoodsDao goodsDao;

	@Override
	public Page queryList(SzlGoods goods) {
		return goodsDao.queryList(goods);
	}

	@Override
	public SzlGoods getById(Integer id, HttpServletRequest request) {
		SzlGoods goods = super.get(SzlGoods.class, id);
		if (goods!=null) {
			if (StringUtils.hasText(goods.getGoodsimage())) {
				goods.setViewgoodsimage(Constants.file_view_path+"/"+Constants.GOODS_COVER_IMAGE_PATH+"/"+"cutmin"+goods.getGoodsimage());
			}
			if (StringUtils.hasText(goods.getQrcodepath())) {
				goods.setViewqrcodepath(Constants.file_view_path+"/"+Constants.GOODS_QRCODE_IMAGE_PATH+"/"+goods.getQrcodepath());
			}
		}
		return goods;
	}

	@Override
	public String saveOrUpdate(SzlGoods goods, MultipartFile goodsimagefile, MultipartFile qrcodepathfile, HttpServletRequest request) {
		if (goods.getIshot()==null) goods.setIshot(2);
		if (goods.getIsrecommend()==null) goods.setIsrecommend(2);
		if (goods.getIsrecommend()==null) goods.setIsrecommend(2);
		if (goods.getTocouponnum()==null) goods.setTocouponnum(0);
		goods.setIsdelete(1);
		goods.setRealtocouponnum(0);
		Assert.isTrue(StringUtils.hasLength(goods.getGoodsname()), "商品名称不能为空!");
		goods.setGoodsname(goods.getGoodsname().trim());
		Assert.isTrue(goods.getClassid()!=null && goods.getClassid()!=0, "商品分类不能为空!");
		Assert.isTrue(goods.getPclassid()!=null && goods.getPclassid()!=0, "商品分类不能为空并且不是一级分类!");
		Assert.isTrue(goods.getGoodssource()!=null, "商品来源不能为空!");
		Assert.isTrue(goods.getCurrentprice()!=null, "商品现价不能为空!");
		Assert.isTrue(goods.getCouponprice()!=null, "商品优惠价不能为空!");
		Assert.isTrue(goods.getCouponafterprice()!=null, "商品优惠券价格不能为空!");//根据现价和优惠价计算优惠后价格
		Assert.isTrue(StringUtils.hasText(goods.getStarttime()), "商品开始时间不能为空!");
		Assert.isTrue(StringUtils.hasText(goods.getEndtime()), "商品结束时间不能为空!");
		Assert.isTrue(StringUtils.hasText(goods.getTolongurl()), "推广长连接不能为空!");
		Assert.isTrue(ValidateUtil.islength(goods.getTolongurl(), 500), "推广长连接不能超过500个字符!");
		goods.setTocouponsurl(goods.getTocouponsurl().trim());
		Assert.isTrue(ValidateUtil.islength(goods.getTocouponsurl(), 500), "推广长连接不能超过500个字符!");
		if (StringUtils.hasText(goods.getTocouponsurl())) goods.setTocouponsurl(goods.getTocouponsurl().trim());
		Assert.isTrue(ValidateUtil.islength(goods.getOrigurl(), 500), "商品路径不能超过500个字符!");
		if (StringUtils.hasText(goods.getOrigurl())) goods.setOrigurl(goods.getOrigurl().trim());
		Assert.isTrue(ValidateUtil.islength(goods.getDescription(), 2500), "推广长连接不能超过2500个字符!");
		if (StringUtils.hasText(goods.getDescription())) goods.setDescription(goods.getDescription().trim());

		SzlGoods newSzlGoods = new SzlGoods();
		if (goods.getId()!=null) {
			newSzlGoods = super.get(SzlGoods.class, goods.getId());
		}
		//上传二维码
		String qrcodepath = Constants.file_upload_path+File.separator+Constants.GOODS_QRCODE_IMAGE_PATH;
		if (qrcodepathfile!=null) {
			String originalFilename = qrcodepathfile.getOriginalFilename();
			String filetype = originalFilename.substring(qrcodepathfile.getOriginalFilename().lastIndexOf(StringUtils.Symbol.POINT)+1);
			if(!CommonUtils.isExistStr(filetype.toLowerCase(), new String[]{"jpg","bmp","gif","png"}))
				throw new CapecException("不允许上传" + filetype + "格式的文件！");
			String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(StringUtils.Symbol.POINT), originalFilename.length());
			File targetFile = new File(qrcodepath, filename);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				qrcodepathfile.transferTo(targetFile);
			} catch (Exception e) {
				log.error(e);
				throw new CapecException("文件上传失败!");
			}
			Assert.isTrue(targetFile.length() / (1024 * 1024) <= Constants.MAX_UPLOAD_IMAGE_SIZE,"上传的图片大小请控制在" + Constants.MAX_UPLOAD_IMAGE_SIZE + "M内!");
			//原图存在，删除原图片
			if (StringUtils.hasText(newSzlGoods.getQrcodepath())) {
				File file = new File(qrcodepath, newSzlGoods.getQrcodepath());
				file.delete();
			}
			goods.setQrcodepath(filename);
		}

		//上传商品封面图
		String goodspath = Constants.file_upload_path+File.separator+Constants.GOODS_COVER_IMAGE_PATH;
		if (goodsimagefile==null) {
			if (StringUtils.notText(goods.getGoodsimage())) {
				throw new CapecException("商品图片不允许为空!");
			}
		}else{
			String originalFilename = goodsimagefile.getOriginalFilename();
			String filetype = originalFilename.substring(goodsimagefile.getOriginalFilename().lastIndexOf(StringUtils.Symbol.POINT)+1);
			if(!CommonUtils.isExistStr(filetype.toLowerCase(), new String[]{"jpg","bmp","gif","png"}))
				throw new CapecException("不允许上传" + filetype + "格式的文件！");
			String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(StringUtils.Symbol.POINT), originalFilename.length());
			File targetFile = new File(goodspath, filename);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				goodsimagefile.transferTo(targetFile);
			} catch (Exception e) {
				log.error(e);
				throw new CapecException("文件上传失败!");
			}
			Assert.isTrue(targetFile.length() / (1024 * 1024) <= Constants.MAX_UPLOAD_IMAGE_SIZE,"上传的图片大小请控制在" + Constants.MAX_UPLOAD_IMAGE_SIZE + "M内!");
			ImageUtil.createThumbnail(targetFile.getPath(), goodspath + File.separator + "cutmin"+filename,Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT1.get(1), Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT1.get(2));
			ImageUtil.createThumbnail(targetFile.getPath(), goodspath + File.separator + "cutmax"+filename,Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT2.get(1), Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT2.get(2));
			//原图存在，删除原图片
			if (StringUtils.hasText(newSzlGoods.getGoodsimage())) {
				File file = new File(goodspath, newSzlGoods.getGoodsimage());
				file.delete();
				file = new File(goodspath, "cutmin"+newSzlGoods.getGoodsimage());
				file.delete();
				file = new File(goodspath, "cutmax"+newSzlGoods.getGoodsimage());
				file.delete();
			}
			goods.setGoodsimage(filename);
		}
		if (goods.getId()==null) {
			super.save(goods);
		}else{
			goods.setCreatetime(newSzlGoods.getCreatetime());
			goods.setCreatetime(newSzlGoods.getCreatetime());
			goods.setIsdelete(newSzlGoods.getIsdelete());
			goods.setCreateperson(newSzlGoods.getCreateperson());
			goods.setCreateperson(newSzlGoods.getCreateperson());
			goods.setRealtocouponnum(newSzlGoods.getRealtocouponnum());
			BeanUtils.copyProperties(goods, newSzlGoods);
			super.update(newSzlGoods,EditType.NULL_UPDATE);
		}
		return null;
	}

	@Override
	public String updateFieidById(String fieid, Integer value, String ids) {
		goodsDao.updateFieidById(fieid, value, ids);
		return "更新成功!";
	}
	
}
