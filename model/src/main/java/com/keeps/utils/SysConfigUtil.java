package com.keeps.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

/** 
 * <p>Title: SysConfigUtil.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class SysConfigUtil {


	public static String getConfig(String key){
		try {
			Properties p = PropertiesLoaderUtils.loadAllProperties("system.properties");
			return p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
