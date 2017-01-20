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

	//状态，1启用，2禁用
	public static final Map<Integer, String> SYSTEM_STATUS = new HashMap<Integer, String>();
	static{
		SYSTEM_STATUS.put(1, "启用");
		SYSTEM_STATUS.put(2, "禁用");
	}
	
	public static final Map<String, Map<Integer, String>> DICT_ITEM_LIST = new HashMap<String, Map<Integer, String>>();
	static{
		DICT_ITEM_LIST.put("status", SYSTEM_STATUS);
	}
}
