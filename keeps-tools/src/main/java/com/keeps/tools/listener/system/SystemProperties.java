package com.keeps.tools.listener.system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * Title: ServletContextListener.java
 * </p>
 * <p>
 * Description: @TODO
 * </p>
 * <p>
 * Copyright: Copyright (c) KEEPS
 * </p>
 * 
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日 修改日期： 修改人： 复审人：
 */
public class SystemProperties implements ServletContextListener {
	public static Map<String, String> map = new HashMap();
	private Log log = LogFactory.getLog(SystemProperties.class);

	public static String get(String key) {
		return ((String) map.get(key));
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		Properties properties = new Properties();
		try {
			properties.load(SystemProperties.class.getClassLoader().getResourceAsStream("system.properties"));
			Iterator i = properties.keySet().iterator();
			while (i.hasNext()) {
				String key = String.valueOf(i.next());
				map.put(key, (String) properties.get(key));
			}
		} catch (FileNotFoundException e) {
			this.log.error("找不到system.properties文件!");
		} catch (IOException e) {
			this.log.error("找不到system.properties文件!");
		}
	}
}
