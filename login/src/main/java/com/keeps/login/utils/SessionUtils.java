package com.keeps.login.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.keeps.tools.utils.UserSchool;

public class SessionUtils{

	public static final String SESSION_DEFAULT_NAME="ns_sianLKJLnvIIN_007";
	
	/**
	  * @Title:			createSession 
	  * @Description:	创建session
	  * @param:
	  * @return: 
	  * @author:		李园园
	  * @data:			2016年11月24日
	 */
	public static void createSession(UserSchool uc,HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_DEFAULT_NAME, uc);
	}
	
	public static void removeUserSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.removeAttribute(SESSION_DEFAULT_NAME);
	}
	
	public static boolean isLogin(HttpServletRequest request){
		HttpSession session = request.getSession();
		UserSchool o = (UserSchool)session.getAttribute(SESSION_DEFAULT_NAME);
		if(o == null){
			return false;
		}
		if(o.getUserid()==null){
			session.removeAttribute(SESSION_DEFAULT_NAME);
			return false;
		}
		return true;
	}
	public static UserSchool getLoginUserSchool(HttpServletRequest request){
		HttpSession session = request.getSession();
		UserSchool o = (UserSchool)session.getAttribute(SESSION_DEFAULT_NAME);
		return o;
	}
	
	
}
