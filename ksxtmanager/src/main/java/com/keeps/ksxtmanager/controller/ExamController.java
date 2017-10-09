package com.keeps.ksxtmanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractAPIController;
import com.keeps.ksxtmanager.system.service.DictService;

/** 
 * <p>Title: ExamController.java</p>  
 * <p>Description: 考试管理 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年9月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RestController
@RequestMapping("exam")
public class ExamController extends AbstractAPIController{


	@Autowired
	private DictService dictService;
	
	/**
	  * @Title:			index 
	  * @Description:	打开我的考试列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年10月7日
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/exam/list");
		return view;
	}
	
}
