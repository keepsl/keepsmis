package com.keeps.core.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.ControllerModelPost;
import com.keeps.tools.utils.ControllerPost;
import com.keeps.tools.utils.JsonPost;

@Component
public abstract class AbstractController {
	protected Log log = LogFactory.getLog(super.getClass());

	public Map<String, Object> doJsonPost(JsonPost jsonPost) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			jsonPost.doInstancePost(map);
			map.put("success", true);
		} catch (CapecException e) {
			map.put("success", false);
			map.put("message", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "系统出现未知错误!");
			this.log.info(e.getMessage());
		}
		return map;
	}

	public String doJsonpPost(String callbackvar, JsonPost jsonPost) {
		Map<String, Object> map = new HashMap<String, Object>();
		Gson g = new Gson();
		try {
			if (StringUtils.isBlank(callbackvar)) {
				callbackvar = "callback";
				this.log.error("callbackvar is null,but callback is default constant,use it.");
			}
			jsonPost.doInstancePost(map);
			map.put("success", Boolean.valueOf(true));
		} catch (CapecException e) {
			map.put("success", Boolean.valueOf(false));
			map.put("message", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", Boolean.valueOf(false));
			map.put("message", "系统出现未知错误!");
			this.log.info(e.getMessage());
		}
		if ((map == null) || (map.isEmpty())) {
			return callbackvar + "()";
		}
		return callbackvar + "(" + g.toJson(map) + ")";
	}

	public void doPost(ControllerPost controllerPost, HttpServletRequest request) {
		try {
			controllerPost.doInstancePost(request);
		} catch (CapecException e) {
			request.setAttribute("message", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "系统出现未知错误!");
			this.log.info(e.getMessage());
		}
	}

	public void doPost(ControllerModelPost controllerModelPost, ModelAndView modelAndView) {
		try {
			controllerModelPost.doInstancePost(modelAndView);
		} catch (CapecException e) {
			modelAndView.addObject("message", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("message", "系统出现未知错误!");
			this.log.info(e.getMessage());
		}
	}

	public void printJSON(String json, HttpServletResponse response) {
		PrintWriter outa = null;
		try {
			Assert.isTrue(response != null, "response为null", true);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			outa = response.getWriter();
			outa.print(json);
		} catch (Exception e) {
			e.printStackTrace();
			outa.close();
			this.log.error(e.getMessage());
		} finally {
			if (outa != null) {
				outa.flush();
				outa.close();
			}
		}
	}
}
