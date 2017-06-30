package com.keeps.tools.utils;

import com.keeps.tools.common.SoftUtils;

/**
 * <p>
 * Title: APIModel.java
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
 * @date 创建日期：2017年6月25日 修改日期： 修改人： 复审人：
 */
public class APIModel implements SoftUtils {
	public static String CODE = "code";
	public static String MSG = "msg";

	public static Integer SYSTEM_SUCCESS = Integer.valueOf(1);
	public static Integer SYSTEM_ERROR = Integer.valueOf(-1);
	public static Integer SYSTEM_UNKNOW = Integer.valueOf(0);
	private Integer code;
	private Object data;

	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
