package com.keeps.tools.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class SpringUtils implements ApplicationContextAware{

	private static Log log = LogFactory.getLog(SpringUtils.class);
	private static ApplicationContext applicationContext;

	public synchronized void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

	private static ApplicationContext getContext() {
		return applicationContext;
	}

	public static Object getBean(String beanId) {
		return getBean(Object.class, beanId);
	}

	public static <T> T getBean(Class<T> clazz, String beanId) throws ClassCastException {
		ApplicationContext context = getContext();
		Assert.isTrue(StringUtils.isNotEmpty(beanId), "beanId must not null!");
		Assert.isTrue(context.containsBean(beanId), "beanId :[" + beanId + "] is not exist!");
		Object bean = null;
		try {
			bean = context.getBean(beanId);
		} catch (RuntimeException e) {
			throw e;
		}
		return (T) bean;
	}

}
