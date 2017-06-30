package com.keeps.crm.utils.pojo;
/** 
 * <p>Title: MessagePojo.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class MessagePojo {

	private String code;	//用于判断返回类别
	private boolean success;//true 成功，false 失败
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
