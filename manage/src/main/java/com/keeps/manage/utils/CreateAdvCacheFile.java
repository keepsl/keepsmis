package com.keeps.manage.utils;

import java.util.List;

import com.keeps.manage.utils.aliyunoss.AliyunOSSClientUtil;
import com.keeps.model.TAdv;
import com.keeps.model.TAdvPosition;
import com.keeps.utils.Constants;

/** 
 * <p>Title: CreateAdvFile.java</p>  
 * <p>Description: 创建广告缓存文件 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月14日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class CreateAdvCacheFile {
	
	public static String ossProcessResize(Integer w,Integer h){
		return "?x-oss-process=image/resize,m_fixed,w_"+w+",h_"+h+"";
	}
	/**
	  * @Title:			createSlideshowAdvPosition 
	  * @Description:	多图片广告位
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月15日
	 */
	public static void createManyImageAdvPosition(String filename,TAdvPosition advPosition){
		StringBuilder sb = new StringBuilder();
		sb.append(" document.write(\" ");
		sb.append("<div href='javascript:;' class='small-banner-item'>");
		sb.append("<a target='_blank' href='javascript:;'>");
		sb.append("<img src='"+Constants.file_view_path+"/"+Constants.ADV_POSITION_IMAGE_PATH+"/"+advPosition.getDefaultContent()+ossProcessResize(advPosition.getApWidth(),advPosition.getApHeight())+"'>");
		sb.append("</a>");
		sb.append("</div>");
		sb.append(" \");");
		AliyunOSSClientUtil.uploadObject2OSS(sb.toString().getBytes(),Constants.ADV_FILE_PATH,filename);
		//FileUtils.createFile(filepath, filename, sb.toString());
	}
	public static void createManyImageAdv(String filename,List<TAdv> list,Integer w,Integer h){
		StringBuilder sb = new StringBuilder();
		sb.append(" document.write(\" ");
		for (TAdv tAdv : list) {
			sb.append("<div href='javascript:;' class='small-banner-item'>");
			sb.append("<a target='_blank' href='"+tAdv.getAdvLink()+"'>");
			sb.append("<img src='"+Constants.file_view_path+"/"+Constants.ADV_IMAGE_PATH+"/"+tAdv.getAdvContent()+ossProcessResize(w,h)+"'>");
			sb.append("</a>");
			sb.append("</div>");
		}
		sb.append(" \");");
		AliyunOSSClientUtil.uploadObject2OSS(sb.toString().getBytes(),Constants.ADV_FILE_PATH,filename);
		//FileUtils.createFile(filepath, filename, sb.toString());
	}
	/**
	  * @Title:			createSlideshowAdvPosition 
	  * @Description:	生成幻灯片的广告文件
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月14日
	 */
	public static void createSlideshowAdvPosition(String filename,TAdvPosition advPosition){
		StringBuilder sb = new StringBuilder();
		sb.append(" document.write(\" ");
		sb.append(" <a class='swiper-slide' href='javascript:;' >  ");
		sb.append(" 	<img src='"+Constants.file_view_path+"/"+Constants.ADV_POSITION_IMAGE_PATH+"/"+advPosition.getDefaultContent()+ossProcessResize(advPosition.getApWidth(),advPosition.getApHeight())+"'>");
		sb.append(" </a>  ");
		sb.append(" \");");
		AliyunOSSClientUtil.uploadObject2OSS(sb.toString().getBytes(),Constants.ADV_FILE_PATH,filename);
		//FileUtils.createFile(filepath, filename, sb.toString());
	}
	public static void createSlideshowAdv(String filename,List<TAdv> list,Integer w,Integer h){
		StringBuilder sb = new StringBuilder();
		sb.append(" document.write(\" ");
		sb.append("<ol>");
    	for (int i = 1; i <= list.size(); i++) {
    		if (i==1) {
    			sb.append("<li class='active'>"+i+"</li>");
			} else {
				sb.append("<li>"+i+"</li>");
			}
		}
    	sb.append("</ol>");
    	
    	sb.append("<ul>");
    	for (TAdv adv : list) {
    		sb.append("<li><a href='"+adv.getAdvLink()+"' target='_blank' style='cursor:pointer'><img src='"+Constants.file_view_path+"/"+Constants.ADV_IMAGE_PATH+"/"+adv.getAdvContent()+ossProcessResize(w,h)+"' width='"+w+"' height='"+h+"'></a></li>");
		}
    	sb.append("</ul>");
		/*for (TAdv tAdv : list) {
			sb.append("<a class='swiper-slide' target='_blank' href='"+tAdv.getAdvLink()+"'>");
			sb.append("<img src='"+Constants.file_view_path+"/"+Constants.ADV_IMAGE_PATH+"/"+tAdv.getAdvContent()+ossProcessResize(w,h)+"'>");
			sb.append("</a>");
		}*/
		sb.append(" \");");
		AliyunOSSClientUtil.uploadObject2OSS(sb.toString().getBytes(),Constants.ADV_FILE_PATH,filename);
		//FileUtils.createFile(filepath, filename, sb.toString());
	}
}
