package com.keeps.tools.utils.threadlocal;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletContext {
	private static final ThreadLocal<ServletContext> CONTEXT_THREAD = new ThreadLocal();
	private Map CONTEXT = null;
	private static final String REQUEST = "javax.servlet.http.HttpServletRequest";
	private static final String RESPONSE = "javax.servlet.http.HttpServletResponse";

	private ServletContext() {
	}

	private ServletContext(Map context) {
		this.CONTEXT = context;
	}

	public static ServletContext getContext() {
		ServletContext c = (ServletContext) CONTEXT_THREAD.get();
		if (c == null) {
			c = new ServletContext(new HashMap());
			CONTEXT_THREAD.set(c);
		}
		return c;
	}

	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
		this.CONTEXT.put("javax.servlet.http.HttpServletRequest", request);
		this.CONTEXT.put("javax.servlet.http.HttpServletResponse", response);
	}

	public void remove() {
		CONTEXT_THREAD.remove();
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) this.CONTEXT.get("javax.servlet.http.HttpServletRequest");
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) this.CONTEXT.get("javax.servlet.http.HttpServletResponse");
	}
}
