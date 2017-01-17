package com.keeps.webservice.utils;

/**
 * <p>Title: ReturnData.java</p>  
 * <p>Description: 请求返回类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class ReturnData {

	private String code="100";//100 成功 其他都是失败
	private String message;
	private Object data;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
