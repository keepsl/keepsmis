package com.keeps.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.manage.service.ArticleService;
import com.keeps.manage.service.ArticleTypeService;
import com.keeps.model.TArticleType;
import com.keeps.tools.utils.JsonPost;

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
	
	/**
	  * @Title:			query 
	  * @Description:	获得栏目列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TArticleType articleType) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", articleTypeService.queryList(articleType));
			}
		});
	}
	
	/**
	  * @Title:			getArticleTypeTree 
	  * @Description:	获得栏目树
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getArticleTypeTree")
	public @ResponseBody Map getArticleTypeTree(final TArticleType articleType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleTypeService.queryListTree());
			}
		});
	}
	
	/**
	  * @Title:			add 
	  * @Description:	打开添加文章栏目页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/articletype/add");
		view.addObject("articletypelist", articleTypeService.queryListAll());
		return view;
	}
	
	/**
	  * @Title:			edit 
	  * @Description:	打开编辑文章栏目页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/articletype/edit");
		view.addObject("articletype", articleTypeService.getById(id));
		view.addObject("articletypelist", articleTypeService.queryListAll());
		return view;
	}
	
	/**
	  * @Title:			save 
	  * @Description:	保存栏目
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public @ResponseBody Map save(final TArticleType articleType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleTypeService.save(articleType));
			}
		});
	}
	
	/**
	  * @Title:			update 
	  * @Description:	更新栏目
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public @ResponseBody Map update(final TArticleType articleType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleTypeService.update(articleType));
			}
		});
	}
	
	/**
	  * @Title:			delete 
	  * @Description:	删除栏目
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月5日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete")
	public @ResponseBody Map delete(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleTypeService.delete(ids));
			}
		});
	}
}
