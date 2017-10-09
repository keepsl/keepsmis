package com.keeps.ksxtmanager.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.ksxtmanager.system.service.RoleService;
import com.keeps.model.TRole;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: RoleController.java</p>  
 * <p>Description: 角色管理 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("role")
public class RoleController extends AbstractController {

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("system/role/list");
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TRole role) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", roleService.queryList(role));
			}
		});
	}
	
	/**
	  * @Title:			roleSelectWin 
	  * @Description:	角色选择窗口
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年6月19日
	 */
	@RequestMapping("roleSelectWin")
	public ModelAndView roleSelectWin(ModelAndView view,HttpServletRequest request, HttpServletResponse response,String roleids) {
		view.addObject("roleids", roleids);
		view.setViewName("system/role/roleSelectWin");
		return view;
	}

	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("system/role/add");
		return view;
	}
	

	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("system/role/edit");
		view.addObject("role", roleService.getById(id));
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public @ResponseBody Map save(HttpServletRequest request,final TRole role){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", roleService.saveOrUpdate(role));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public @ResponseBody Map update(HttpServletRequest request,final TRole role){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", roleService.saveOrUpdate(role));
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
				arg0.put("message", roleService.delete(ids));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getRoleTree")
	public @ResponseBody Map getRoleTree(String roleids) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", roleService.getRoleTree(roleids));
			}
		});
	}
	
}
