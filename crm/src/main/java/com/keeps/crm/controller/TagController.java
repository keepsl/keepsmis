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
import com.keeps.crm.service.ArticleTypeService;
import com.keeps.crm.service.TagService;
import com.keeps.model.TTag;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: TagController.java</p>  
 * <p>Description: 标签控制类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("tag")
public class TagController extends AbstractController {

	
	@Autowired
	private TagService tagService;
	@Autowired
	private ArticleTypeService articleTypeService;
	/**
	  * @Title:			index 
	  * @Description:	打开标签页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/tag/list");
		return view;
	}

	/**
	  * @Title:			query 
	  * @Description:	查询标签列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TTag tag) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", tagService.queryList(tag));
			}
		});
	}
	
	/**
	  * @Title:			add 
	  * @Description:	打开添加标签页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer typeid) {
		view.setViewName("manager/tag/add");
		view.addObject("typeid", typeid);
		view.addObject("articletypelist", articleTypeService.queryListAll());
		return view;
	}
	
	/**
	  * @Title:			edit 
	  * @Description:	打开编辑标签页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/tag/edit");
		view.addObject("tag", tagService.getById(id));
		view.addObject("articletypelist", articleTypeService.queryListAll());
		return view;
	}
	
	
	/**
	  * @Title:			save 
	  * @Description:	保存标签
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public @ResponseBody Map save(final TTag tag){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", tagService.save(tag));
			}
		});
	}
	
	/**
	  * @Title:			update 
	  * @Description:	更新标签
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public @ResponseBody Map update(final TTag tag){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", tagService.update(tag));
			}
		});
	}
	
	/**
	  * @Title:			delete 
	  * @Description:	删除标签
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete")
	public @ResponseBody Map delete(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", tagService.delete(ids));
			}
		});
	}
}
