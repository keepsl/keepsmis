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
import com.keeps.ksxtmanager.system.service.ScheduleService;
import com.keeps.model.TSchedule;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: ScheduleController.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年7月1日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("schedule")
public class ScheduleController extends AbstractController {

	@Autowired
	private ScheduleService scheduleService;
	

	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("system/schedule/list");
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TSchedule schedule) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", scheduleService.query(schedule));
			}
		});
	}

	@RequestMapping("show")
	public ModelAndView show(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.addObject("schedule", scheduleService.getById(id));
		view.setViewName("system/schedule/view");
		return view;
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		TSchedule schedule = new TSchedule();
		schedule.setScheduletime(DateUtils.getNow());
		view.addObject("schedule", schedule);
		view.setViewName("system/schedule/add");
		return view;
	}
	

	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("system/schedule/edit");
		view.addObject("schedule", scheduleService.getById(id));
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public @ResponseBody Map save(HttpServletRequest request,final TSchedule schedule){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", scheduleService.saveOrUpdate(schedule));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public @ResponseBody Map update(HttpServletRequest request,final TSchedule schedule){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", scheduleService.saveOrUpdate(schedule));
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
				arg0.put("message", scheduleService.delete(ids));
			}
		});
	}
}
