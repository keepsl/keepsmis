package com.keeps.crm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractAPIController;
import com.keeps.crm.service.ClientService;
import com.keeps.crm.service.EmpService;
import com.keeps.utils.Constants;

/** 
 * <p>Title: StatisticsController.java</p>  
 * <p>Description: 统计控制类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月24日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RestController
@RequestMapping("statistics")
public class StatisticsController extends AbstractAPIController{
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private EmpService empService;
	
	@RequestMapping("empclientindex")
	public ModelAndView empclientindex(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/emp/clientStatistics");
		view.addObject("websiteUrl", Constants.websiteUrl);
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("empClient")
	public Map empClient() {
		Map m = success();
		m.put("recored", empService.getClientGroupByEmp());
		return m;
	}
	
	@RequestMapping("empcontactindex")
	public ModelAndView empcontactindex(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/emp/contactStatistics");
		view.addObject("websiteUrl", Constants.websiteUrl);
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("empContact")
	public Map empContact(String contacttimesta,String contacttimeend) {
		Map m = success();
		m.put("recored", empService.getContactGroupByEmp(contacttimesta,contacttimeend));
		return m;
	}
}
