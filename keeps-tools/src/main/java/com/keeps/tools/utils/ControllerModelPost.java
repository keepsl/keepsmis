package com.keeps.tools.utils;

import org.springframework.web.servlet.ModelAndView;

public abstract interface ControllerModelPost {
	
	public abstract void doInstancePost(ModelAndView paramModelAndView);
	
}
