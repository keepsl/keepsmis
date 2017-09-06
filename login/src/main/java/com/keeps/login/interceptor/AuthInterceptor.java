package com.keeps.login.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.keeps.login.service.UserSchoolService;
import com.keeps.login.utils.UserLoginUtils;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.SpringUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.UserSchool;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;

public class AuthInterceptor extends HandlerInterceptorAdapter{
	private static Log log = LogFactory.getLog(AuthInterceptor.class);
	private String loginUrl;
	private String logoutUrl;
	private String noauthUrl;
	private static String[] NOT_INTERCEP_METHOD =  new String[]{"page/index","page/main","page/logout","org/getOrgTree"
			,"emp/changePwd","emp/updatePwd","client/downloadTemplate","page/downloadkj"};

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String id = UserLoginUtils.ifLoginReturnUid(request, response);
		if(StringUtils.hasText(id)){
			UserSchool uc = null;
			/*if(SessionUtils.isLogin(request)){
				uc = SessionUtils.getLoginUserSchool(request);
				UserSchoolThread.set(uc);
			}else{*/
				uc = getUserSchoolService().getUserSchool(id);
				if (uc==null || uc.getUserid()==null) {
					log.error("用户:"+id+"在cache中存在，但是在数据库查询时查不到他的信息。参看：UserSchoolDaoImpl.getUserSchool");
					//此时应该清空cache并返回登录页面
					UserLoginUtils.removeLoginStatus(request, response);
					goLoginPage(request, response);
					return false;
				}else{
					//SessionUtils.createSession(uc, request);
					if (uc.getIsadmin().intValue()==1) {
						uc.setSuperAdmin(true);
					}else{
						uc.setSuperAdmin(false);
						if(!hasAuthorization(uc.getUserid(),  request, response)){
							returnErrorMessage(response,"您没有该操作权限!");
							/*//此时应该清空cache并返回登录页面
							UserLoginUtils.removeLoginStatus(request, response);
							goLoginPage(request, response);*/
							return false;
						}
					}
					UserSchoolThread.set(uc);
				}
			//}
			return true;
		}else{
			goLoginPage(request, response);
			return false;
		}
	}
	
	private boolean hasAuthorization(Integer userid, HttpServletRequest req, HttpServletResponse res) throws IOException{
		try {
			String url = getCleanRequestUrl(req.getRequestURI());
			//判断访问的连接是否需要权限验证。
			if (CommonUtils.isExistStr(url, NOT_INTERCEP_METHOD)) {
				return true;
			}
			System.out.println(url+"===");
			boolean hasAuthority = getUserSchoolService().hasAuthorizition(userid,url);
			return hasAuthority;
		} catch (Exception e) {
			log.error("验证权限出错:", e);
		}
		return false;
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
		System.out.println(url);
		return url.indexOf(";")>-1?url.substring(0, url.indexOf(";")):url;
	}
	
	
	private String getCleanRequestUrl(String requestURI) {
		System.out.println(requestURI+"===");
		if(requestURI.indexOf(";")>-1){
			requestURI = requestURI.substring(0, requestURI.indexOf(";"));
		}
		requestURI = requestURI.substring(1,requestURI.length());
		requestURI = requestURI.substring(requestURI.indexOf("/")+1,requestURI.length());
		return requestURI.trim();
	}
	
	private void returnErrorMessage(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        /*ObjectMapper mapper = new ObjectMapper();
        Map<String, String> result = new HashMap<>();
        result.put("code", "0");
        result.put("message", errorMessage);*/
        Map<String, Object> res = new HashMap<>();
        res.put("success", false);
        res.put("message", errorMessage);
        //String jsonOfRST = "{\"result\":" + mapper.writeValueAsString(result) + ",\"data\":null,\"message\":" +errorMessage+"}";
        out.print(new Gson().toJson(res));
        out.flush();
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
