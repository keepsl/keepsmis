package com.keeps.crm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.crm.service.DictService;
import com.keeps.crm.service.NewsService;
import com.keeps.model.TNews;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.JsonPost;
import com.keeps.utils.Constants;

/** 
 * <p>Title: NewsController.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年7月1日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("news")
public class NewsController extends AbstractController {

	@Autowired
	private NewsService newsService;
	@Autowired
	private DictService dictService;
	

	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/news/list");
		view.addObject("typelist", dictService.getDictTypeByCode(Constants.DICT_CODE[5]));
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TNews news) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", newsService.query(news));
			}
		});
	}

	@RequestMapping("show")
	public ModelAndView show(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.addObject("news", newsService.getById(id));
		view.setViewName("manager/news/view");
		return view;
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		TNews news = new TNews();
		news.setPublishtime(DateUtils.getNow());
		view.addObject("news", news);
		view.addObject("typelist", dictService.getDictTypeByCode(Constants.DICT_CODE[5]));
		view.setViewName("manager/news/add");
		return view;
	}
	

	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/news/edit");
		view.addObject("typelist", dictService.getDictTypeByCode(Constants.DICT_CODE[5]));
		view.addObject("news", newsService.getById(id));
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public @ResponseBody Map save(HttpServletRequest request,final TNews news){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", newsService.saveOrUpdate(news));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public @ResponseBody Map update(HttpServletRequest request,final TNews news){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", newsService.saveOrUpdate(news));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete")
	public @ResponseBody Map delete(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", newsService.delete(ids));
			}
		});
	}
	
}
