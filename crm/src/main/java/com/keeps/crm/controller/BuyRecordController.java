package com.keeps.crm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.crm.service.BuyRecordService;
import com.keeps.crm.service.EmpService;
import com.keeps.model.TBuyRecord;
import com.keeps.model.TEmp;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.JsonPost;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;

/** 
 * <p>Title: BuyRecord.java</p>  
 * <p>Description: 客户购买记录 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("buyrecord")
public class BuyRecordController extends AbstractController{

	@Autowired
	private BuyRecordService buyRecordService;

	@Autowired
	private EmpService empService;
	
	@RequestMapping("index/{operType}")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response, @PathVariable("operType")Integer operType) {
		view.setViewName("manager/buyRecord/list");
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("query/{operType}")
	public @ResponseBody Map query(final TBuyRecord buyRecord, @PathVariable("operType")Integer operType) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", buyRecordService.queryList(buyRecord));
			}
		});
	}
	
	@RequestMapping("stream")
	public ModelAndView stream(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/buyRecordStream/list");
		TEmp emp = new TEmp();
		if (!UserSchoolThread.get().isSuperAdmin()) {
			emp.setId(UserSchoolThread.get().getUserid());
		}
		view.addObject("emplist", empService.getList(emp));
		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("queryStream")
	public @ResponseBody Map queryStream(final TBuyRecord buyRecord) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", buyRecordService.queryStreamList(buyRecord));
			}
		});
	}
	
	@RequestMapping("add/{operType}")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response, Integer clientid, @PathVariable("operType")Integer operType) {
		view.setViewName("manager/buyRecord/add");
		view.addObject("clientid", clientid);
		view.addObject("operType", operType);
		view.addObject("updatetimestr", DateUtils.formatNow());
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("save/{operType}")
	public @ResponseBody Map save(final TBuyRecord buyRecord, @PathVariable("operType")Integer operType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message",  buyRecordService.saveOrUpdate(buyRecord));
			}
		});
	}

	
}
