package com.keeps.tools.utils;

import java.io.Serializable;

public class UserSchool implements Serializable {
	public static String NO_BODY = "nobody";
	private static final long serialVersionUID = -2830199892969953280L;
	private Integer userid;
	private String loginid;
	private String nickname;
	private String email;
	private Integer phone;
	private boolean isadmin;

	public UserSchool() {
	}
	public UserSchool(Integer userid, String nickname, String email, Integer phone) {
		this.userid = userid;
		this.nickname = nickname;
		this.email = email;
		this.phone = phone;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public boolean isIsadmin() {
		return isadmin;
	}
	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}

}
