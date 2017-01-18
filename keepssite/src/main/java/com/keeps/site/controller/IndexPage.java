package com.keeps.site.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
/** 
 * <p>Title: IndexPage.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */

@Controller
@RequestMapping("indexPage")
public class IndexPage extends AbstractController {

	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("page/index");
		return view;
	}
}
