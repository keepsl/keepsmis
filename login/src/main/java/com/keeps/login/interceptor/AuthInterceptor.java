package com.keeps.login.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.keeps.login.service.UserSchoolService;
import com.keeps.login.utils.SessionUtils;
import com.keeps.login.utils.UserLoginUtils;
import com.keeps.tools.utils.SpringUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.UserSchool;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	private static Log log = LogFactory.getLog(AuthInterceptor.class);
	private String loginUrl;
	private String logoutUrl;
	private String noauthUrl;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String id = UserLoginUtils.ifLoginReturnUid(request, response);
		if(StringUtils.hasText(id)){
			UserSchool uc = null;
			if(SessionUtils.isLogin(request)){
				uc = SessionUtils.getLoginUserSchool(request);
				UserSchoolThread.set(uc);
			}else{
				uc = getUserSchoolService().getUserSchool(id);
				if (uc.getUserid()==null) {
					log.error("用户:"+id+"在cache中存在，但是在数据库查询时查不到他的信息。参看：UserSchoolDaoImpl.getUserSchool");
					//此时应该清空cache并返回登录页面
					UserLoginUtils.removeLoginStatus(request, response);
					goLoginPage(request, response);
					return false;
				}else{
					SessionUtils.createSession(uc, request);
					UserSchoolThread.set(uc);
				}
			}
			return true;
		}else{
			goLoginPage(request, response);
			return false;
		}
	}

	private UserSchoolService getUserSchoolService(){
		return SpringUtils.getBean(UserSchoolService.class, "userSchoolService");
	}
	
	private void goLoginPage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		StringBuffer sbpage = new StringBuffer();
		sbpage.append(loginUrl);
		sbpage.append("?service=");
		sbpage.append(getCleanRequestUrl(request));
		if(StringUtils.hasText(request.getQueryString())){
			sbpage.append("?");
			sbpage.append(request.getQueryString());
		}
		response.sendRedirect(sbpage.toString());
	}
	
	
	private String getCleanRequestUrl(HttpServletRequest req){
		String url = req.getRequestURL().toString();
		return url.indexOf(";")>-1?url.substring(0, url.indexOf(";")):url;
	}
	
	
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getNoauthUrl() {
		return noauthUrl;
	}

	public void setNoauthUrl(String noauthUrl) {
		this.noauthUrl = noauthUrl;
	}

}
