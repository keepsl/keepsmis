package com.keeps.nas.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keeps.core.controller.AbstractController;
import com.keeps.login.utils.UserLoginUtils;
import com.keeps.model.TUser;
import com.keeps.nas.service.UserService;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.CookieUtils;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.LoginPageVar;
import com.keeps.tools.utils.SpringUtils;
import com.keeps.tools.utils.ValidateCode;

/**
 * 
 * <p>Title: LoginController.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2016年12月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
public class LoginController extends AbstractController {

	@Autowired
	private UserService userService;

	@RequestMapping("login")
	public String login(LoginPageVar loginvar, HttpServletRequest request, HttpServletResponse response) {
		if (loginvar == null) {
			loginvar = new LoginPageVar();
		}
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		if (UserLoginUtils.ifLoginReturnUid(request, response) != null) {
			request.setAttribute("service", loginvar.getService());
			return "redirect:" + url + "/keeps/page/index";
		}
		try {
			if (StringUtils.isBlank(loginvar.getLt())) {
				return "page/login";
			}
			String cookscaptcha = CookieUtils.getCookieValue(request, "scaptcha");
			UserService us = SpringUtils.getBean(UserService.class, "userService");
			TUser user = us.keepLogin(loginvar.getLoginid(), loginvar.getPwd(), loginvar.getCode(),
					cookscaptcha);
			loginvar.setId(user.getId());
			UserLoginUtils.keepLoginStatus(loginvar, request, response);
		} catch (CapecException cape) {
			request.setAttribute("errormsg", cape.getMessage());
			return "page/login";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errormsg", "系统出现未知错误!");
			return "page/login";
		} finally {
			request.setAttribute("loginid", loginvar.getLoginid());
			request.setAttribute("lt", DateUtils.formatNow(DateUtils.DEFALUT_DATETIME_PATTERN_THREE));
			request.setAttribute("service", loginvar.getService());
		}
		return "redirect:"+url+"/manage/page/index";
	}

	/**
	 * @Title: generateScaptcha
	 * @Description: 获得登录验证码
	 * @param:
	 * @return:
	 * @author: keeps
	 * @data: 2017年1月9日
	 */
	@RequestMapping("scaptcha")
	public void generateScaptcha(HttpServletRequest request, HttpServletResponse response) {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		ValidateCode instance = new ValidateCode();
		CookieUtils.setCookie(response, "scaptcha", instance.getCode().toUpperCase(), null, -1);
		try {
			instance.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
