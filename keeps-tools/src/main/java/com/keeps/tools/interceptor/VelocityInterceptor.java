package com.keeps.tools.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class VelocityInterceptor extends HandlerInterceptorAdapter {
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			String contextpath = request.getContextPath();
			int port = request.getServerPort();

			String main_path = request.getScheme() + "://" + request.getServerName();

			if (port != 80) {
				main_path = main_path + ":" + request.getServerPort();
			}
			String path = main_path + contextpath;
			request.setAttribute("path", path);
			request.setAttribute("main_path", main_path);
		}
	}
}
