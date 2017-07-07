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
import com.keeps.crm.service.ContactRecordService;
import com.keeps.crm.service.DictService;
import com.keeps.crm.service.EmpService;
import com.keeps.model.TBuyRecord;
import com.keeps.model.TContactRecord;
import com.keeps.model.TEmp;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.JsonPost;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;
import com.keeps.utils.Constants;

/** 
 * <p>Title: ContactRecordController.java</p>  
 * <p>Description: 客户联系记录 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("contactrecord")
public class ContactRecordController extends AbstractController{


	@Autowired
	private ContactRecordService contactRecordService;
	@Autowired
	private DictService dictService;
	@Autowired
	private EmpService empService;
	
	@RequestMapping("index/{operType}")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer clientid, @PathVariable("operType")Integer operType) {
		view.addObject("clientid", clientid);
		view.addObject("operType", operType);
		view.addObject("updatetimestr", DateUtils.formatNow());
		view.setViewName("manager/contactRecord/list");
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("query/{operType}")
	public @ResponseBody Map query(final TContactRecord contactRecord, @PathVariable("operType")Integer operType) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", contactRecordService.queryList(contactRecord));
			}
		});
	}

	@RequestMapping("stream")
	public ModelAndView stream(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/contactRecordStream/list");
		TEmp emp = new TEmp();
		if (!UserSchoolThread.get().isSuperAdmin()) {
			emp.setId(UserSchoolThread.get().getUserid());
		}
		view.addObject("emplist", empService.getList(emp));
		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("queryStream")
	public @ResponseBody Map queryStream(final TContactRecord contactRecord) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", contactRecordService.queryStreamList(contactRecord));
			}
		});
	}
	

	@RequestMapping("statistics")
	public ModelAndView statistics(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/contactRecordStatistics/list");
		TEmp emp = new TEmp();
		if (!UserSchoolThread.get().isSuperAdmin()) {
			emp.setId(UserSchoolThread.get().getUserid());
		}
		view.addObject("emplist", empService.getList(emp));
		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("queryStatistics")
	public @ResponseBody Map queryStatistics(final TContactRecord contactRecord) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", contactRecordService.queryStatisticsList(contactRecord));
			}
		});
	}
	
	@RequestMapping("add/{operType}")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response, Integer clientid, @PathVariable("operType")Integer operType) {
		view.setViewName("manager/contactRecord/add");
		view.addObject("clientid", clientid);
		view.addObject("operType", operType);
		view.addObject("contacttimestr", DateUtils.formatNow("yyyy-MM-dd HH:mm"));
		//view.addObject("clienttypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		//view.addObject("clientstarslist", dictService.getDictByCode(Constants.DICT_CODE[2]));
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("save/{operType}")
	public @ResponseBody Map save(final TContactRecord contactRecord, @PathVariable("operType")Integer operType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", contactRecordService.saveOrUpdate(contactRecord));
			}
		});
	}
	
}
