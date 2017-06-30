package com.keeps.utils;

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
	//排除的拦截菜单
	public static String[] NOT_INTERCEP_METHOD =  new String[]{"page/index","page/main","page/logout"};
	
	//字典类型code
	public static String[] DICT_CODE =  new String[]{"client_type","product_category","client_stars","news_type","receive_type"};

	//网站URL
	public static String websiteUrl = "";
	//项目路径  系统初始化设置
	public static String realPath = "";
	//文件上传路径
	public static String uploadPath = "";
	//文件上传服务器路径
	public static String file_upload_path="";
	//文件访问服务器路径
	public static String file_view_path="";
	//上传和预览服务器上的文件夹
	public static String file_folder="";

	//省着来商品分页数量
	public static int goods_page_rows=40;


	//广告生成文件路径
	public static String ADV_FILE_PATH = "file/adv/cache";

	//文章封面上传路径
	public static String ARTICLE_COVER_IMAGE_PATH = "article/image";
	//商品图片路径
	public static String GOODS_COVER_IMAGE_PATH = "goods/image";
	public static final Map<Integer, Integer> GOODS_CUT_IMAGE_WIDTH_HEIGHT1 = new HashMap<Integer, Integer>();
	static{
		GOODS_CUT_IMAGE_WIDTH_HEIGHT1.put(1, 400);
		GOODS_CUT_IMAGE_WIDTH_HEIGHT1.put(2, 400);
	}
	public static final Map<Integer, Integer> GOODS_CUT_IMAGE_WIDTH_HEIGHT2 = new HashMap<Integer, Integer>();
	static{
		GOODS_CUT_IMAGE_WIDTH_HEIGHT2.put(1,800);
		GOODS_CUT_IMAGE_WIDTH_HEIGHT2.put(2, 800);
	}
	
	//广告位默认图片路径
	public static String ADV_POSITION_IMAGE_PATH = "advPosition/image";
	//宽高组
	public static final Map<Integer, Integer> ADV_POSITION_CUT_IMAGE_WIDTH_HEIGHT = new HashMap<Integer, Integer>();
	static{
		ADV_POSITION_CUT_IMAGE_WIDTH_HEIGHT.put(1, 660);
		ADV_POSITION_CUT_IMAGE_WIDTH_HEIGHT.put(2, 360);
	}
	
	//广告默认图片路径
	public static String ADV_IMAGE_PATH = "adv/image";
	public static final Map<Integer, Integer> ADV_CUT_IMAGE_WIDTH_HEIGHT = new HashMap<Integer, Integer>();
	static{
		ADV_CUT_IMAGE_WIDTH_HEIGHT.put(1, 660);
		ADV_CUT_IMAGE_WIDTH_HEIGHT.put(2, 360);
	}
	
	//商品领券二维码路径
	public static String GOODS_QRCODE_IMAGE_PATH = "goods/qrcode/image";
		
	//封面图宽高
	public static final Integer MAX_COVER_IMAGE_WIDTH = 220;
	public static final Integer MAX_COVER_IMAGE_HEIGHT = 150;

	//上传图片大小单位M
	public static final Integer MAX_UPLOAD_IMAGE_SIZE = 2;
	
	//开关状态，1是，2否
	public static final Map<Integer, String> SYSTEM_OPEN_CLOSE = new HashMap<Integer, String>();
	static{
		SYSTEM_OPEN_CLOSE.put(1, "是");
		SYSTEM_OPEN_CLOSE.put(2, "否");
	}
	
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
	//商品来源
	public static final Map<Integer, String> GOODS_SOURCE = new HashMap<Integer, String>();
	static{
		GOODS_SOURCE.put(1, "淘宝");
		GOODS_SOURCE.put(2, "天猫");
		GOODS_SOURCE.put(3, "京东");
	}
	
	//广告为类别
	public static final Map<Integer, String> ADV_POSITION_CLASS = new HashMap<Integer, String>();
	static{
		ADV_POSITION_CLASS.put(1, "图片");
		ADV_POSITION_CLASS.put(2, "文字");
		ADV_POSITION_CLASS.put(3, "幻灯");
		ADV_POSITION_CLASS.put(4, "Flash");
	}
	
	//广告位展示方式
	public static final Map<Integer, String> ADV_POSITION_DISPLAY = new HashMap<Integer, String>();
	static{
		ADV_POSITION_DISPLAY.put(1, "幻灯片");
		ADV_POSITION_DISPLAY.put(2, "多广告展示");
		ADV_POSITION_DISPLAY.put(3, "单广告展示");
	}
	
	//角色类型，1管理角色，2业务角色
	public static final Map<Integer, String> ORLE_TYPE = new HashMap<Integer, String>();
	static{
		ORLE_TYPE.put(1, "管理角色");
		ORLE_TYPE.put(2, "业务角色");
	}
	
	//1正常，2冻结，3离职
	public static final Map<Integer, String> EMP_STATUS = new HashMap<Integer, String>();
	static{
		EMP_STATUS.put(1, "正常");
		EMP_STATUS.put(2, "冻结");
		EMP_STATUS.put(3, "离职");
	}
	//通用
	public static final Map<String, Map<Integer, String>> DICT_ITEM_LIST = new HashMap<String, Map<Integer, String>>();
	static{
		DICT_ITEM_LIST.put("dic_open_close", SYSTEM_OPEN_CLOSE);
		DICT_ITEM_LIST.put("dic_status", SYSTEM_STATUS);
		DICT_ITEM_LIST.put("dic_template_type", TEMPLATE_TYPE);
		DICT_ITEM_LIST.put("dic_goods_source", GOODS_SOURCE);
		DICT_ITEM_LIST.put("dic_adv_position_class", ADV_POSITION_CLASS);
		DICT_ITEM_LIST.put("dic_adv_position_display", ADV_POSITION_DISPLAY);
		DICT_ITEM_LIST.put("dic_orletype", ORLE_TYPE);
		DICT_ITEM_LIST.put("dic_emp_status", EMP_STATUS);
	}

}