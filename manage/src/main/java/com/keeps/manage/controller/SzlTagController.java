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
import com.keeps.manage.service.SzlTagService;
import com.keeps.model.SzlTag;
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
@RequestMapping("szltag")
public class SzlTagController extends AbstractController {

	
	@Autowired
	private SzlTagService szlTagService;
	
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/szl/tag/list");
		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final SzlTag tag) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", szlTagService.queryList(tag));
			}
		});
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer typeid) {
		view.setViewName("manager/szl/tag/add");
		return view;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/szl/tag/edit");
		view.addObject("tag", szlTagService.getById(id));
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("saveOrUpdate")
	public @ResponseBody Map saveOrUpdate(final SzlTag tag){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", szlTagService.saveOrUpdate(tag));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIshotById")
	public @ResponseBody Map updateIshotById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", szlTagService.updateFieidById("ishot",value,id));
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
				arg0.put("message", szlTagService.delete(ids));
			}
		});
	}
}
