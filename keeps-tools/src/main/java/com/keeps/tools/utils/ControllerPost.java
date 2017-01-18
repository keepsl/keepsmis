package com.keeps.tools.utils;

import javax.servlet.http.HttpServletRequest;

public abstract interface ControllerPost {
	
	public abstract void doInstancePost(HttpServletRequest paramHttpServletRequest);

}
