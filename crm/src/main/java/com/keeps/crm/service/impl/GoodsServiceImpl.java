package com.keeps.crm.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.service.AbstractService;
import com.keeps.crm.Processor.CouponPageProcessor;
import com.keeps.crm.dao.GoodsClassDao;
import com.keeps.crm.dao.GoodsDao;
import com.keeps.crm.service.GoodsService;
import com.keeps.crm.utils.RegexUtils;
import com.keeps.crm.utils.pojo.MessagePojo;
import com.keeps.model.SzlGoods;
import com.keeps.model.SzlGoodsClass;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.ExcelUtil;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.Constants;
import com.keeps.utils.ValidateUtil;

import us.codecraft.webmagic.Spider;

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
	private ReentrantLock lock = new ReentrantLock();  
	public static Integer uplength = 0;
	public static String upcontent="正在上传中...";
	
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private GoodsClassDao goodsClassDao;
	@Override
	public Page queryList(SzlGoods goods) {
		Page page = goodsDao.queryList(goods);
		return page;
	}

	@Override
	public SzlGoods getById(Long id, HttpServletRequest request) {
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
		goods.setClicknum(0);
		if (goods.getIshot()==null) goods.setIshot(2);
		if (goods.getIsrecommend()==null) goods.setIsrecommend(2);
		if (goods.getTocouponnum()==null) goods.setTocouponnum(0);
		goods.setIsdelete(1);
		goods.setRealtocouponnum(0);
		Assert.isTrue(StringUtils.hasText(goods.getGoodsname()), "商品名称不能为空!");
		goods.setGoodsname(goods.getGoodsname().trim());
		Assert.isTrue(goods.getClassid()!=null && goods.getClassid()!=0, "商品分类不能为空!");
		Assert.isTrue(goods.getPclassid()!=null && goods.getPclassid()!=0, "商品分类不能为空并且不是一级分类!");
		Assert.isTrue(goods.getGoodssource()!=null, "商品来源不能为空!");
		Assert.isTrue(goods.getCurrentprice()!=null, "商品现价不能为空!");
		Assert.isTrue(goods.getCouponprice()!=null, "商品优惠价不能为空!");
		Assert.isTrue(goods.getCouponafterprice()!=null, "商品优惠券价格不能为空!");//根据现价和优惠价计算优惠后价格
		Assert.isTrue(StringUtils.hasText(goods.getStarttime()), "商品开始时间不能为空!");
		Assert.isTrue(StringUtils.hasText(goods.getEndtime()), "商品结束时间不能为空!");
		
		Assert.isTrue(StringUtils.hasText(goods.getGoodsimageurl()), "商品图片路径不能为空!");
		Assert.isTrue(ValidateUtil.islength(goods.getGoodsimageurl(), 250), "商品图片路径不能超过500个字符!");
		goods.setGoodsimageurl(goods.getGoodsimageurl().trim());
		
		Assert.isTrue(StringUtils.hasText(goods.getTolongurl()), "推广长连接不能为空!");
		Assert.isTrue(ValidateUtil.islength(goods.getTolongurl(), 500), "推广长连接不能超过500个字符!");
		goods.setTocouponsurl(goods.getTocouponsurl().trim());
		
		Assert.isTrue(StringUtils.hasText(goods.getTocouponsurl()), "优惠券连接不能为空!");
		Assert.isTrue(ValidateUtil.islength(goods.getTocouponsurl(), 500), "优惠券连接不能超过500个字符!");
		goods.setTocouponsurl(goods.getTocouponsurl().trim());
		
		Assert.isTrue(ValidateUtil.islength(goods.getOrigurl(), 500), "商品路径不能超过500个字符!");
		if (StringUtils.hasText(goods.getOrigurl())) goods.setOrigurl(goods.getOrigurl().trim());
		
		Assert.isTrue(ValidateUtil.islength(goods.getDescription(), 2500), "描述不能超过2500个字符!");
		if (StringUtils.hasText(goods.getDescription())) goods.setDescription(goods.getDescription().trim());
		if(!goodsDao.isUnique(goods, new String[]{"goodsname","isdelete"})){
			throw new CapecException("["+goods.getGoodsname()+"]商品名称已经存在,不允许重复添加!");
		}
		SzlGoods newSzlGoods = new SzlGoods();
		if (goods.getId()!=null) {
			newSzlGoods = super.get(SzlGoods.class, goods.getId());
		}
		/*if (qrcodepathfile!=null) {
			//上传二维码
			String qrcodepath = Constants.file_upload_path+File.separator+Constants.GOODS_QRCODE_IMAGE_PATH;
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
		}*/

		/*if (goodsimagefile==null) {
			if (StringUtils.notText(goods.getGoodsimage())) {
				throw new CapecException("商品图片不允许为空!");
			}
		}else{
			//上传商品封面图
			String goodspath = Constants.file_upload_path+File.separator+Constants.GOODS_COVER_IMAGE_PATH;
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
			ImageUtils.cut4(targetFile.getPath(), goodspath + File.separator + "cutmin"+filename,Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT1.get(1), Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT1.get(2));
			ImageUtils.cut4(targetFile.getPath(), goodspath + File.separator + "cutmax"+filename,Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT2.get(1), Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT2.get(2));
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
		}*/
		if (goods.getId()==null) {
			goods.setDataflag(1);
			super.save(goods);
		}else{
			goods.setCreatetime(newSzlGoods.getCreatetime());
			goods.setIsdelete(newSzlGoods.getIsdelete());
			goods.setCreateperson(newSzlGoods.getCreateperson());
			goods.setRealtocouponnum(newSzlGoods.getRealtocouponnum());
			goods.setClicknum(newSzlGoods.getClicknum());
			goods.setDataflag(newSzlGoods.getDataflag());
			BeanUtils.copyProperties(goods, newSzlGoods);
			super.update(newSzlGoods,EditType.NULL_UPDATE);
		}
		return null;
	}
	
	public String saveAnalysisToCopywriter(Integer classid,Integer pclassid,Integer goodssource,String copywriter){
		Assert.isTrue(classid!=null && classid!=0, "商品分类不能为空!");
		Assert.isTrue(pclassid!=null && pclassid!=0, "商品分类不能为空并且不是一级分类!");
		Assert.isTrue(goodssource!=null && goodssource!=0, "商品来源不能为空!");
		Assert.isTrue(StringUtils.hasText(copywriter), "商品文案或连接不能为空!");
		copywriter = copywriter.trim();
/*		Assert.isTrue(StringUtils.hasText(copywriter), "商品文案不能为空!");
		Assert.isTrue(copywriter.indexOf("在售价")!=-1, "没有解析出在售价!");
		Assert.isTrue(copywriter.indexOf("券后价")!=-1, "没有解析出券后价!");
		Assert.isTrue(copywriter.indexOf("下单链接")!=-1, "没有解析出下单连接!");
		//解析 出在售价、券后价
		List<String> values = RegexUtils.getValues(copywriter, "】", "元");
		Assert.isTrue(CollectionUtils.isNotEmpty(values), "没有解析出在售价、券后价!");
		if (values.size()!=2) {
			throw new CapecException("在售价、券后价有一项为空、或有多余项!");
		}*/
		//下单链接
		List<String> tocouponsurl = RegexUtils.getValues(copywriter, "】", "\r\n-");
		//Assert.isTrue(CollectionUtils.isNotEmpty(tocouponsurl), "没有解析出下单链接!");

		List<String> taokouling = RegexUtils.getValues(copywriter, "￥", "￥");
		//Assert.isTrue(CollectionUtils.isNotEmpty(taokouling), "没有解析出淘口令!");
		
		//SzlGoods goods = new SzlGoods();
		
		String url = "";
		if (CollectionUtils.isNotEmpty(tocouponsurl)) {//说明是文案
			SzlGoods newgoods = new SzlGoods();
			newgoods.setIsdelete(1);
			newgoods.setGoodsname(copywriter.substring(0, copywriter.indexOf("【包邮】")).trim());
			if(!goodsDao.isUnique(newgoods, new String[]{"goodsname","isdelete"})){
				throw new CapecException("["+newgoods.getGoodsname()+"]商品名称已经存在,不允许重复添加!");
			}
			url = tocouponsurl.get(0);
		}else{
			url = copywriter;
		}
		
		//解析出图片连接
		Spider.create(new CouponPageProcessor()).addUrl(url)
        .thread(1)
        .run();
		SzlGoods goods = CouponPageProcessor.goods;
		Assert.isTrue(goods!=null, "解析失败商品文案或连接不正确!");
		goods.setIsdelete(1);
		if (CollectionUtils.isEmpty(tocouponsurl)) {//说明是文案
			if(!goodsDao.isUnique(goods, new String[]{"goodsname","isdelete"})){
				throw new CapecException("["+goods.getGoodsname()+"]商品名称已经存在,不允许重复添加!");
			}
		}
		goods.setClassid(classid);
		goods.setPclassid(pclassid);
		goods.setGoodssource(goodssource);
/*		goods.setGoodsname(copywriter.substring(0, copywriter.indexOf("【包邮】")).trim());
		goods.setCurrentprice(Float.valueOf(values.get(0)));
		goods.setCouponafterprice(Float.valueOf(values.get(1)));
		goods.setCouponprice(goods.getCurrentprice()-goods.getCouponafterprice());
		goods.setTocouponsurl(tocouponsurl.get(0));*/
		if (CollectionUtils.isNotEmpty(taokouling)) {
			goods.setTaokouling("￥"+taokouling.get(0)+"￥");
		}
		goods.setDataflag(2);
		goods.setClicknum(0);
		goods.setIsdelete(1);
		goods.setRealtocouponnum(0);
		goods.setIshot(2);
		goods.setIsrecommend(2);
		goods.setTocouponnum(0);
		super.save(goods);
		return "解析成功!";
	}

	@Override
	public String updateFieidById(String fieid, Integer value, String ids) {
		goodsDao.updateFieidById(fieid, value, ids);
		return "更新成功!";
	}
	
	public String saveGoodsfileData(MultipartFile file, HttpServletRequest request){
		if(lock.isLocked()){
			throw new CapecException("有其他管理员正在进行操作,为防止数据冲突,请您稍等一会儿再来进行该操作.");
		}
		lock.lock();
		uplength = 0;
		upcontent = "正在校验文件格式...";
		Assert.isTrue(file!=null, "请选择文件!");
		Assert.isTrue(!file.isEmpty(), "上传的文件为空!");
		String filename = file.getOriginalFilename();
		String filetype = filename.substring(file.getOriginalFilename().lastIndexOf(".")+1);
		if(!CommonUtils.isExistStr(filetype.toLowerCase(), new String[]{"xls","xlsx"}))
			throw new CapecException("不允许上传" + filetype + "格式的文件!");
		Workbook wb  = null;
		Set<String> imagespath = new HashSet<String>();
		try {
			//判断execl版本
			if(file.getOriginalFilename().toLowerCase().endsWith("xls")){//xls 2003包括2003之前的版本
				wb = new HSSFWorkbook(file.getInputStream());
			}else if(file.getOriginalFilename().toLowerCase().endsWith("xlsx")){
				wb = new XSSFWorkbook(file.getInputStream());
			}
			uplength = 10;
			upcontent = "正在校验文件格式...";
			Assert.isTrue(wb.getNumberOfSheets()>=0, "没有找到sheet!");
			Sheet sheet = wb.getSheetAt(0);
			Assert.isTrue(sheet!=null, "sheet不能为空!");
			/*for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			}*/
			validateSheetTitle(sheet.getRow(0));
			List<SzlGoods> listgoods = new ArrayList<>();
			List<MessagePojo> listmessage = new ArrayList<>();
			uplength = 50;
			upcontent = "正在校验文件数据...";
			sheet2bean(sheet,listgoods,listmessage);
			
			uplength = 70;
			upcontent = "正在整理数据...";
			/*for (SzlGoods goods : listgoods) {
				String path = Constants.file_upload_path+File.separator+Constants.GOODS_COVER_IMAGE_PATH;
				String imagename = UUID.randomUUID()+".jpg";
				String goodspath = path+File.separator+imagename;
				imagespath.add(goodspath);
				boolean bo = ImageRequest.saveImage(goods.getGoodsimage(), path,imagename);
				if (bo) {
					ImageUtils.cut4(goodspath, path + File.separator + "cutmin"+imagename,Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT1.get(1), Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT1.get(2));
					ImageUtils.cut4(goodspath, path + File.separator + "cutmax"+imagename,Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT2.get(1), Constants.GOODS_CUT_IMAGE_WIDTH_HEIGHT2.get(2));
					goods.setGoodsimage(imagename);
				}else{
					goods.setGoodsimage("error");
				}
			}*/
			upcontent = "正在保存数据...";
			super.saveOrUpdateAllEntity(listgoods, EditType.NULL_UN_UPDATE);
			uplength = 100;
			upcontent = "数据保存完成...";
		} catch (Exception e) {
			File file2 = null;
			for (String image : imagespath) {
				file2 = new File(image);
				file2.delete();
			}
			log.error("导入商品失败:"+e);
			e.printStackTrace();
			throw new CapecException("导入商品失败:"+e.getMessage());
		} finally {
			lock.unlock();
		}
		return "导入成功!";
	}
	
	private boolean sheet2bean(Sheet sheet,List<SzlGoods> listgoods,List<MessagePojo> listmessage){
		boolean zflag = true;//总标记 当有一处错时，标记为false。
		String message = "";
		SzlGoods goods = new SzlGoods();
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			for (int cellNum = 0; cellNum <= 12; cellNum++) {
			//for (Cell cell : row) {
				Cell cell = row.getCell(cellNum);
				//取得值
				String value = ExcelUtil.getValue(cell);
				if (cellNum!=11 && cellNum!=12) {//商品路径、商品描述可为空
					if (StringUtils.notText(value)) {
						message = impRetMsg(rowNum,cellNum,"不能为空");
						throw new CapecException(message);
					}
				}
				// 把row转换为bean
				switch (cellNum) {
					case 0 :
						goods = new SzlGoods();
						goods.setIshot(2);
						goods.setIsdelete(1);
						goods.setIsrecommend(2);
						goods.setClicknum(0);
						//根据大分类找到分类ID
						SzlGoodsClass goodsClass = goodsClassDao.getByClassname(value);
						if (goodsClass==null) {
							message = impRetMsg(rowNum,cellNum,"找不到该大分类");
							throw new CapecException(message);
						}
						if (goodsClass.getPid()!=0) {
							message = impRetMsg(rowNum,cellNum,"大分类不能填写小分类");
							throw new CapecException(message);
						}
						goods.setPclassid(goodsClass.getId());
						break;
					case 1:
						//根据大分类找到分类ID
						goodsClass = goodsClassDao.getByClassname(value);
						if (goodsClass==null) {
							message = impRetMsg(rowNum,cellNum,"找不到该小分类");
							throw new CapecException(message);
						}
						if (goodsClass.getPid()==0) {
							message = impRetMsg(rowNum,cellNum,"小分类不能填写大分类");
							throw new CapecException(message);
						}
						goods.setClassid(goodsClass.getId());
						break;
					case 2://商品名
						Integer countgoods = goodsDao.getCountByGoodsname(value);
						if (countgoods>0) {
							message = impRetMsg(rowNum,cellNum,"该商品已经存在");
							throw new CapecException(message);
						}
						goods.setGoodsname(value);
						break;
					case 3://商品来源
						if (CommonUtils.isNumeric(value)){
							String goodsSource = Constants.GOODS_SOURCE.get(Integer.parseInt(value));
							if (StringUtils.notText(goodsSource)) {
								message = impRetMsg(rowNum,cellNum,"商品来源填写不正确（淘宝、天猫、京东）");
								throw new CapecException(message);
							}
							goods.setGoodssource(Integer.parseInt(value));
						}else{
							if (Constants.GOODS_SOURCE.get(1).equals(value)) {
								goods.setGoodssource(1);
							}else if(Constants.GOODS_SOURCE.get(2).equals(value)){
								goods.setGoodssource(2);
							}else if(Constants.GOODS_SOURCE.get(3).equals(value)){
								goods.setGoodssource(3);
							}else{
								message = impRetMsg(rowNum,cellNum,"商品来源填写不正确（淘宝、天猫、京东）");
								throw new CapecException(message);
							}
						}
						break;
					case 4://优惠开始时间
						goods.setStarttime(value);
						break;
					case 5://优惠天数
						//根据优惠天数计算结束时间
						try {
							Integer daynum = Integer.parseInt(value);
							goods.setEndtime(DateUtils.format(DateUtils.addDay(DateUtils.parse(goods.getStarttime(), "yyyy-MM-dd"), daynum)));
						} catch (NumberFormatException e) {
							message = impRetMsg(rowNum,cellNum,"优惠天数格式不正确");
							throw new CapecException(message);
						}
						break;
					case 6://商品价格
						try {
				        	DecimalFormat df = new DecimalFormat("#.##");
							goods.setCurrentprice(Float.parseFloat(df.format(Float.parseFloat(value))));
						} catch (NumberFormatException e) {
							message = impRetMsg(rowNum,cellNum,"商品价格格式不正确");
							throw new CapecException(message);
						}
						break;
					case 7://优惠价格
						try {
				        	DecimalFormat df = new DecimalFormat("#.##");
							goods.setCouponprice(Float.parseFloat(df.format(Float.parseFloat(value))));
						} catch (NumberFormatException e) {
							message = impRetMsg(rowNum,cellNum,"优惠价格格式不正确");
							throw new CapecException(message);
						}
						break;
					case 8://商品图片连接
						if (value.indexOf(".jpg")!=-1) {
							goods.setGoodsimageurl(value.substring(0, value.lastIndexOf(".jpg")+4));
						}else{
							goods.setGoodsimageurl(value);
						}
						break;
					case 9://推广长连接
						goods.setTolongurl(value);
						break;
					case 10://优惠券长连接
						goods.setTocouponsurl(value);
						break;
					case 11://商品路径
						goods.setOrigurl(value);
						break;
					case 12://商品描述
						goods.setDescription(value);
						if (isExistsByImp(goods,listgoods)) {
							//计算打折后价格
							goods.setCouponafterprice(goods.getCurrentprice() - goods.getCouponprice());
							goods.setTocouponnum(0);
							goods.setRealtocouponnum(0);
							listgoods.add(goods);
						}
						break;
					default:
						break;
				}
			}
		}
		return zflag;
	}
	private boolean isExistsByImp(SzlGoods goods, List<SzlGoods> list) {
		// 判断是否在当前excel中存在
		for (SzlGoods mo : list) {
			String mostr = mo.getGoodsname();
			if (mostr.equals(goods.getGoodsname())) {
				return false;
			}
		}
		return true;
	}
	private String impRetMsg(int rowindex,int cellindex,String msg){
		return "数据格式错误:第" + (rowindex+1)+ "行,第"+(cellindex+1)+"列["+getTitieName(cellindex)+"],"+msg+".";
	}
	private String validateSheetTitle(Row row){
		if(row==null){
			throw new CapecException("标题行不能为空");
		}
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("标题格式错误:<br/>");
		boolean zflag = true;
		for(Integer i=0;i<=12;i++) {//循环读取每个单元格
			Cell cell = row.getCell(i);
			if (CommonUtils.isNull(cell)) {
				sbBuffer.append("第"+(i+1)+"列必须为["+getTitieName(i)+"].<br/>");
			}
			if(!ExcelUtil.getValue(row.getCell(i)).equals(getTitieName(i))){
				zflag = false;
				sbBuffer.append("第"+(i+1)+"列必须为["+getTitieName(i)+"].<br/>");
			}
		}
		if (!zflag) {
			throw new CapecException(sbBuffer.toString());
		}
		return null;
	}
	
	public static String getTitieName(int i){
		String[] titlename = new String[]{"商品大分类","商品小分类","商品名称","商品来源","优惠开始时间","优惠天数","商品价格（元）","优惠价格（元）","商品图片连接","推广长连接","优惠券长连接","商品路径","商品描述"};
		return titlename[i].trim();
	}
	
}
