package com.keeps.webservice.enums;

import org.apache.commons.lang.StringUtils;

import com.keeps.webservice.service.TaobaoService;


/**
 * <p>Title: ReflectEnum.java</p>  
 * <p>Description: 基础映射类 </p>  
 * 1. 此方法将接口cmd值与Service处理类对应起来
 * 2. 新增接口时，在此类中增加cmd与SERVICE类对应关系即可
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public enum ReflectEnum {

	/** 定时任务调度 sta **/
	parsecourseinfo(TaobaoService.class,"loadMarkTest"),
	

	/**
	 *
	 */
	/********************未完待续**********************************/

	;

	private ReflectEnum(Class cls, String value) {
		this.cls = cls;
		this.value = value;
	}

	private Class cls;
	private String value;

	public Class getCls() {
		return cls;
	}

	public String getValue() {
		return value;
	}

	// 普通方法
	public static Class getServiceName(String index) {
		for (ReflectEnum c : ReflectEnum.values()) {
			if(StringUtils.contains(c.getValue(), index)){
				return c.cls;
			}
		}
		return null;
	}

}
