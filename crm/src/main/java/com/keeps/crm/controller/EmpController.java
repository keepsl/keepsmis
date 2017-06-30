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
import com.keeps.crm.service.EmpService;
import com.keeps.crm.service.OrgService;
import com.keeps.model.TEmp;
import com.keeps.model.TOrg;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: EmpController.java</p>  
 * <p>Description: 员工管理 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("emp")
public class EmpController extends AbstractController {

	@Autowired
	private EmpService empService;
	@Autowired
	private OrgService orgSerivce;
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/emp/list");
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TEmp emp) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", empService.queryList(emp));
			}
		});
	}
	
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer orgid) {
		view.addObject("orglist", orgSerivce.getList(new TOrg()));
		view.addObject("orgid", orgid);
		view.setViewName("manager/emp/add");
		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("resetPwd")
	public @ResponseBody Map resetPwd(HttpServletRequest request,final Integer id){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", empService.updatePwdById(id));
			}
		});
	}
	
	
	@RequestMapping("changePwd")
	public ModelAndView changePwd(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/emp/changePwd");
		return view;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping("updatePwd")
	public @ResponseBody Map updatePwd(HttpServletRequest request,final String oldPwd,final String newPwd,final String checkNewPwd){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", empService.updatePwd(oldPwd,newPwd,checkNewPwd));
			}
		});
	}
	

	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/emp/edit");
		view.addObject("orglist", orgSerivce.getList(new TOrg()));
		view.addObject("emp", empService.getById(id));
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public @ResponseBody Map save(HttpServletRequest request,final TEmp emp){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", empService.saveOrUpdate(emp));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public @ResponseBody Map update(HttpServletRequest request,final TEmp emp){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", empService.saveOrUpdate(emp));
			}
		});
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getEmpTree")
	public @ResponseBody Map getEmpTree(String empids) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", empService.getEmpTree(empids));
			}
		});
	}
	
	/**
	  * @Title:			empSelectWin 
	  * @Description:	客户选择负责人时打开人员选择窗口,根据传入type区分是否可以多选 1多选，2单选
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年6月19日
	 */
	@RequestMapping("empSelectWin")
	public ModelAndView empSelectWin(ModelAndView view,HttpServletRequest request, HttpServletResponse response,String empids,Integer type) {
		//view.addObject("clientid", clientid);
		view.addObject("empids", empids);
		view.addObject("type", type);
		view.setViewName("manager/emp/empSelectWin");
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete")
	public @ResponseBody Map delete(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", empService.delete(ids));
			}
		});
	}
	
	
	
}
