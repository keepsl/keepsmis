package com.keeps.crm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keeps.core.controller.AbstractAPIController;
import com.keeps.crm.service.ClientService;
import com.keeps.crm.service.EmpService;

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
	/**
	  * @Title:			empClient 
	  * @Description:	员工客户量统计
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年6月24日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("empClient")
	public Map empClient() {
		Map m = success();
		m.put("recored", empService.getClientGroupByEmp());
		return m;
	}
}
