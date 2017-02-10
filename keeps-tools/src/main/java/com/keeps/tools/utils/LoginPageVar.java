package com.keeps.tools.utils;

import java.io.Serializable;

/**
 * 
 * <p>Title: CookieUtil.java</p>  
 * <p>Description: 用户登录提交信息 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2016年12月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class LoginPageVar implements Serializable{
    private static final long serialVersionUID = -2830199892969953280L;

	public static Integer Remberme_time = 60 * 24 * 16;// 16天
	private Integer id;
	private String loginid;
	private String pwd;
	private Integer rbm;// 记住我
	private String code;// 验证码
	private String lt;// 提交页面
	private String service;// 请求返回的page

	public Integer getRbm() {
		return rbm;
	}

	public void setRbm(Integer rbm) {
		this.rbm = rbm;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getLt() {
		return lt;
	}

	public void setLt(String lt) {
		this.lt = lt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
