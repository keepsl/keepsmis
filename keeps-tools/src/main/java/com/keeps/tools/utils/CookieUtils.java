package com.keeps.tools.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * <p>Title: CookieUtil.java</p>  
 * <p>Description: cookie操作工具类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2016年12月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class CookieUtils {
	private static Logger log = Logger.getLogger(CookieUtils.class);
	public static final int maxAge=1800;

	public static String getCookieId(HttpServletRequest req, String cookieName) {
		Cookie[] cookies = req.getCookies();
		try {
			if (cookies == null || cookies.length <= 0)
				return null;
			for (Cookie cookie : cookies) {
				if (cookie == null)
					continue;
				if (cookieName.equals(cookie.getName())) {
					String v = cookie.getValue();
					log.info("从cookie中取到了cookieid:" + v);
					return URLDecoder.decode(v, "utf-8").trim();
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从cookie中获取值Cookie
	 * @param request
	 * @param cookieName
	 * @return 
	 */
	public static Cookie getCookie(HttpServletRequest request,String cookieName){
		Cookie[]cookies=request.getCookies();
		if(null==cookies||cookies.length==0)return null;
		for(Cookie cookie:cookies){
			if(cookie.getName().equalsIgnoreCase(cookieName))return cookie;
		}
		return  null;
	}
	
	/**
	 * 从cookie中获取值
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,String cookieName){
		Cookie cookie=getCookie(request,cookieName);
		if(null!=cookie)return cookie.getValue();
		return null;
	}
	
	/**
	 * 添加Cookie
	 * @param response
	 * @param name 名称
	 * @param value 值
	 * @param path 路径
	 * @param maxAge 有效期
	 */
	public static void setCookie(HttpServletResponse response,String name,String value,String path,String domain,int maxAge){
		Cookie cookie=new Cookie(name,"");		
		cookie.setValue(value);
		cookie.setPath(path);
		if(StringUtils.isNotBlank(domain))cookie.setDomain(domain);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	/**
	 * 添加Cookie
	 * @param response
	 * @param name 名称
	 * @param value 值
	 * @param path 路径
	 * @param maxAge 有效期
	 */
	public static void setCookie(HttpServletResponse response,String name,String value,String path,int maxAge){
		try {
			value = URLEncoder.encode(value,"UTF-8");
			Cookie cookie=new Cookie(name,value);		
			cookie.setPath(path);
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加Cookie
	 * @param response
	 * @param name 名称
	 * @param value 值
	 * @param maxAge 有效期
	 */
	public static void setCookie(HttpServletResponse response,String name,String value){
		Cookie cookie=new Cookie(name,"");		
		cookie.setValue(value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	/**
	 * 添加Cookie(path：默认/,maxAge:2000-可自行修改)
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response,String name,String value,int maxAge){
		Cookie cookie = new Cookie(name, value);
		cookie.setValue(value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	/**
	 * 清除Cookie
	 * @param response
	 * @param name 名称
	 * @param path 路径
	 */
	public static void clearCookie(HttpServletResponse response,String name,String path){
		Cookie cookie=new Cookie(name,"");
		cookie.setPath(path);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}
