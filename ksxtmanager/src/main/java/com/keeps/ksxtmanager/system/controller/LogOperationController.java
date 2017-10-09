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
import com.keeps.ksxtmanager.system.service.LogOperationService;
import com.keeps.model.TLogOperation;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: LogController.java</p>  
 * <p>Description: 操作日志 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("log")
public class LogOperationController extends AbstractController {
	
	@Autowired
	private LogOperationService logOperationService;
	
	/**
	  * @Title:			index 
	  * @Description:	操作日志页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月5日
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("system/log/list");
		return view;
	}
	
	/**
	  * @Title:			query 
	  * @Description:	查询操作日志信息
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月5日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TLogOperation logOperation) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", logOperationService.queryList(logOperation));
			}
		});
	}
}
