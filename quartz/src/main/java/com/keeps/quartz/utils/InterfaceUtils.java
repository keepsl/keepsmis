package com.keeps.quartz.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * <p>Title: InterfaceUtils.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class InterfaceUtils {
	private static Properties props = new Properties();

	public static Properties init(String propertyName) {
		try {
			props.load(
					Thread.currentThread().getContextClassLoader().getResourceAsStream("requestinterface.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
}