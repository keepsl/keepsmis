package com.keeps.login.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.keeps.security.SecurityControl;
import com.keeps.tools.utils.CookieUtils;
import com.keeps.tools.utils.LoginPageVar;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class UserLoginUtils {
	private static Logger log = Logger.getLogger(UserLoginUtils.class);

	public static final String COOKIE_DEFAULT_NAME="keeps";
	private static final String COOKIE_SEPARATOR = "~";

	/**
	  * @Title:			keepLoginStatus 
	  * @Description:	保持用户登录
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月11日
	 */
	public static void keepLoginStatus(LoginPageVar logvar, HttpServletRequest request, HttpServletResponse response) {
		try {
			String random = logvar.getId() + "~" + request.getHeader("USER-AGENT");
			Integer maxage = Integer.valueOf(-1);
			if (logvar.getRbm() != null && 1 == logvar.getRbm().intValue()) {
				log.info(logvar.getId() + "登录，选择了rember me 功能。");
				maxage = Integer.valueOf(LoginPageVar.Remberme_time.intValue() * 60);
			} else {
				log.info(logvar.getId() + "登录，没有选择rember me 功能。");
				//3600 一小时
				maxage = 6*60*60;//半天
			}
			CookieUtils.setCookie(response,COOKIE_DEFAULT_NAME,URLEncoder.encode(SecurityControl.getInstance().encode(random), "UTF-8"),maxage);
		} catch (Exception e) {
			log.error("登录时保存cookie出现异常!"+e);
			e.printStackTrace();
		}
	}
	
	/**
	  * @Title:			removeLoginStatus 
	  * @Description:	清除cookie
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月11日
	 */
	public static void removeLoginStatus(HttpServletRequest request,HttpServletResponse response){
		try {
			String cookieid = CookieUtils.getCookieValue(request, COOKIE_DEFAULT_NAME);
			if(StringUtils.isBlank(cookieid)){
				return ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			CookieUtils.clearCookie(response, COOKIE_DEFAULT_NAME, "/");
		}
	}
	
	
	/**
	  * @Title:			ifLoginReturnUid 
	  * @Description:	判断cookie是否存在
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月11日
	 */
	public static String ifLoginReturnUid(HttpServletRequest request,HttpServletResponse response){
		try {
			String cookievalue = CookieUtils.getCookieValue(request, COOKIE_DEFAULT_NAME);
			if(StringUtils.isBlank(cookievalue)){
				return null;
			}
			String[] userinfo = SecurityControl.getInstance().decode(URLDecoder.decode(cookievalue, "utf-8")).split(COOKIE_SEPARATOR);
			if(userinfo.length!=2){
				return null;
			}
			String agent = request.getHeader("USER-AGENT");
			if(!agent.equals(userinfo[1])){
				return null;
			}
			//重新创建cookie
			String random = userinfo[0]+COOKIE_SEPARATOR+userinfo[1];
			CookieUtils.setCookie(response,COOKIE_DEFAULT_NAME,URLEncoder.encode(SecurityControl.getInstance().encode(random), "UTF-8"),3600);
			return userinfo[0];
		} catch (Exception e) {
			log.error("判断cookie是否存在出现异常!"+e);
			e.printStackTrace();
		}
		return "";
	}
}
