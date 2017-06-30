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
import com.keeps.crm.service.OrgService;
import com.keeps.model.TOrg;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: OrgController.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("org")
public class OrgController extends AbstractController{

	@Autowired
	private OrgService orgService;

	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/org/list");
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TOrg org) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", orgService.queryList(org));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getOrgTree")
	public @ResponseBody Map getOrgTree() {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", orgService.getOrgTree());
			}
		});
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer pid) {
		view.addObject("orglist", orgService.getList(new TOrg()));
		view.addObject("pid", pid);
		view.setViewName("manager/org/add");
		return view;
	}

	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/org/edit");
		view.addObject("orglist", orgService.getList(new TOrg()));
		view.addObject("org", orgService.getById(id));
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public @ResponseBody Map save(HttpServletRequest request,final TOrg org){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", orgService.saveOrUpdate(org));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public @ResponseBody Map update(HttpServletRequest request,final TOrg org){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", orgService.saveOrUpdate(org));
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
				arg0.put("message", orgService.delete(ids));
			}
		});
	}
}
