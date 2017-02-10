package com.keeps.manage.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.manage.service.ArticleService;
import com.keeps.manage.service.ArticleTypeService;
import com.keeps.manage.service.TagService;
import com.keeps.model.TArticle;
import com.keeps.model.TTag;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: ArticleController.java</p>  
 * <p>Description: 文章控制类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("article")
public class ArticleController extends AbstractController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleTypeService articleTypeService;
	@Autowired
	private TagService tagService;
	
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
		view.setViewName("manager/article/list");
		return view;
	}
	
	/**
	  * @Title:			query 
	  * @Description:
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TArticle article) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", articleService.queryList(article));
			}
		});
	}
	
	/**
	  * @Title:			add 
	  * @Description:	新增文章
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer typeid) {
		view.setViewName("manager/article/add");
		view.addObject("typeid", typeid);
		TArticle article = new TArticle();
		article.setClicknum(0);
		article.setPublishnickname("小马");
		article.setSource("原创");
		article.setPublishtime(DateUtils.format(DateUtils.getNow(), "yyyy-MM-dd HH:mm:ss"));
		view.addObject("article",article);
		view.addObject("articletypelist", articleTypeService.queryListAll());
		List<TTag> taglist = tagService.queryAll();
		view.addObject("taglist",taglist);
		return view;
	}
	/**
	  * @Title:			edit 
	  * @Description:	编辑文章
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/article/edit");
		view.addObject("article",articleService.getById(id));
		view.addObject("articletypelist", articleTypeService.queryListAll());
		List<TTag> taglist = tagService.queryAll();
		view.addObject("taglist",taglist);
		return view;
	}
	/**
	  * @Title:			saveOrUpdate 
	  * @Description:	发布文章
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("saveOrUpdate")
	public @ResponseBody Map saveOrUpdate(HttpServletRequest request,final TArticle article,final @RequestParam(value = "coverfile", required = false) MultipartFile coverfile){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleService.saveOrUpdate(article,coverfile,request));
			}
		});
	}
	
	/**
	  * @Title:			delete 
	  * @Description:	逻辑删除文章
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete")
	public @ResponseBody Map delete(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleService.updateFieidById("isdelete",2,ids));
			}
		});
	}
	
	/**
	  * @Title:			updateIsshowById 
	  * @Description:	是否显示
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIsshowById")
	public @ResponseBody Map updateIsshowById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleService.updateFieidById("isshow",value,id));
			}
		});
	}
	
	/**
	  * @Title:			updateIshotById 
	  * @Description:	是否热门
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIshotById")
	public @ResponseBody Map updateIshotById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleService.updateFieidById("ishot",value,id));
			}
		});
	}
	
	/**
	  * @Title:			updateIshotById 
	  * @Description:	是否推荐
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIsrecommendById")
	public @ResponseBody Map updateIsrecommendById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleService.updateFieidById("isrecommend",value,id));
			}
		});
	}
	
	/**
	  * @Title:			updateIsrecommendById 
	  * @Description:	是否发布
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIspublishById")
	public @ResponseBody Map updateIspublishById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", articleService.updateFieidById("ispublish",value,id));
			}
		});
	}
	
}
