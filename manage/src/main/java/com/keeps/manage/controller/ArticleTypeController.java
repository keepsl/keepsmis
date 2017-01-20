package com.keeps.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.manage.service.ArticleService;
import com.keeps.manage.service.ArticleTypeService;

/** 
 * <p>Title: ArticleController.java</p>  
 * <p>Description: 文章分类控制类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("articletype")
public class ArticleTypeController extends AbstractController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleTypeService articleTypeService;

	/**
	  * @Title:			index 
	  * @Description:	文章管理页面
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月19日
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/articletype/list");
		return view;
	}
}
