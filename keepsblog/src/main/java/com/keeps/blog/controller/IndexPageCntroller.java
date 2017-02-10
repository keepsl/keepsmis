package com.keeps.blog.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.blog.service.ArticleService;
import com.keeps.blog.service.ArticleTypeService;
import com.keeps.blog.service.TagService;
import com.keeps.core.controller.AbstractController;
import com.keeps.model.TArticleType;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: IndexPage.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("page")
public class IndexPageCntroller extends AbstractController {
	
	@Autowired
	private TagService tagService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("page/index");
		view.addObject("taghotlist", tagService.queryTopHotList());
		view.addObject("articleHotlist",articleService.queryTopHotList());
		view.addObject("articleNewlist",articleService.queryTopNewList());
		view.addObject("articleRecommendlist",articleService.queryTopRecommendList());
		return view;
	}
	
	/**
	  * @Title:			getArticleTypeTree 
	  * @Description:	获得栏目
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月10日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("menu")
	public @ResponseBody Map getListMenu(HttpServletRequest request, HttpServletResponse response){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleTypeService.queryListMenu());
			}
		});
	}
}
