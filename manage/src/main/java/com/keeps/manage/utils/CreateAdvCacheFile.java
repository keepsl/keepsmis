package com.keeps.manage.utils;

import java.util.List;


import com.keeps.model.TAdv;
import com.keeps.model.TAdvPosition;
import com.keeps.tools.utils.FileUtils;
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
	
	/**
	  * @Title:			createSlideshowAdvPosition 
	  * @Description:	多图片广告位
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月15日
	 */
	public static void createManyImageAdvPosition(String filepath,String filename,TAdvPosition advPosition){
		StringBuilder sb = new StringBuilder();
		sb.append(" document.write(\" ");
		sb.append("<div href='javascript:;' class='small-banner-item'>");
		sb.append("<a target='_blank' href='javascript:;'>");
		sb.append("<img src='"+Constants.file_view_path+"/"+Constants.ADV_POSITION_IMAGE_PATH+"/cut"+advPosition.getDefaultContent()+"'>");
		sb.append("</a>");
		sb.append("</div>");
		sb.append(" \");");
		FileUtils.createFile(filepath, filename, sb.toString());
	}
	public static void createManyImageAdv(String filepath,String filename,List<TAdv> list){
		StringBuilder sb = new StringBuilder();
		sb.append(" document.write(\" ");
		for (TAdv tAdv : list) {
			sb.append("<div href='javascript:;' class='small-banner-item'>");
			sb.append("<a target='_blank' href='"+tAdv.getAdvLink()+"'>");
			sb.append("<img src='"+Constants.file_view_path+"/"+Constants.ADV_IMAGE_PATH+"/cut"+tAdv.getAdvContent()+"'>");
			sb.append("</a>");
			sb.append("</div>");
		}
		sb.append(" \");");
		FileUtils.createFile(filepath, filename, sb.toString());
	}
	/**
	  * @Title:			createSlideshowAdvPosition 
	  * @Description:	生成幻灯片的广告文件
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月14日
	 */
	public static void createSlideshowAdvPosition(String filepath,String filename,TAdvPosition advPosition){
		StringBuilder sb = new StringBuilder();
		sb.append(" document.write(\" ");
		sb.append(" <a class='swiper-slide' href='javascript:;' >  ");
		sb.append(" 	<img src='"+Constants.file_view_path+"/"+Constants.ADV_POSITION_IMAGE_PATH+"/cut"+advPosition.getDefaultContent()+"'>");
		sb.append(" </a>  ");
		sb.append(" \");");
		FileUtils.createFile(filepath, filename, sb.toString());
	}
	public static void createSlideshowAdv(String filepath,String filename,List<TAdv> list){
		StringBuilder sb = new StringBuilder();
		sb.append(" document.write(\" ");
		for (TAdv tAdv : list) {
			sb.append("<a class='swiper-slide' target='_blank' href='"+tAdv.getAdvLink()+"'>");
			sb.append("<img src='"+Constants.file_view_path+"/"+Constants.ADV_IMAGE_PATH+"/cut"+tAdv.getAdvContent()+"'>");
			sb.append("</a>");
		}
		sb.append(" \");");
		FileUtils.createFile(filepath, filename, sb.toString());
	}
}
