package com.keeps.manage.utils;

import java.util.HashMap;
import java.util.Map;

/** 
 * <p>Title: Constants.java</p>  
 * <p>Description: 基本常量类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class Constants {
	//项目路径  系统初始化设置
	public static String realPath = "";
	//文件上传路径
	public static String uploadPath = "";
	
	//文章封面上传路径
	public static String ARTICLE_COVER_IMAGE_PATH = "article/cover/image";
	//封面图宽高
	public static final Integer MAX_COVER_IMAGE_WIDTH = 220;
	public static final Integer MAX_COVER_IMAGE_HEIGHT = 150;

	//上传图片大小单位M
	public static final Integer MAX_UPLOAD_IMAGE_SIZE = 2;
	
	//状态，1启用，2禁用
	public static final Map<Integer, String> SYSTEM_STATUS = new HashMap<Integer, String>();
	static{
		SYSTEM_STATUS.put(1, "启用");
		SYSTEM_STATUS.put(2, "禁用");
	}
	//栏目模版类型1：列表页，2内容页
	public static final Map<Integer, String> TEMPLATE_TYPE = new HashMap<Integer, String>();
	static{
		TEMPLATE_TYPE.put(1, "列表页");
		TEMPLATE_TYPE.put(2, "内容页");
	}
	public static final Map<String, Map<Integer, String>> DICT_ITEM_LIST = new HashMap<String, Map<Integer, String>>();
	static{
		DICT_ITEM_LIST.put("dic_status", SYSTEM_STATUS);
		DICT_ITEM_LIST.put("dic_template_type", TEMPLATE_TYPE);
	}

}