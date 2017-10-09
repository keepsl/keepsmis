package com.keeps.ksxtmanager.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.keeps.core.controller.AbstractAPIController;
import com.keeps.ksxtmanager.model.KsQuestions;
import com.keeps.ksxtmanager.service.QuestionsService;
import com.keeps.ksxtmanager.system.service.DictService;
import com.keeps.ksxtmanager.utils.Constants;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: TopicController.java</p>  
 * <p>Description: 题目管理控制类 </p>  
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
@RequestMapping("questions")
public class QuestionsController extends AbstractAPIController{

	@Autowired
	private QuestionsService questionsService;
	@Autowired
	private DictService dictService;
	
	
	/**
	  * @Title:			selectWin 
	  * @Description:试题选择窗口
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年10月6日
	 */
	@RequestMapping("selectWin")
	public ModelAndView selectWin(ModelAndView view,HttpServletRequest request, HttpServletResponse response,String questionsids) {
		view.setViewName("manager/questions/selectWin");
		view.addObject("degofdifftypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("questionstypejson", new Gson().toJson(dictService.getDicttypeTreeByCode(Constants.DICT_CODE[1])));
		view.addObject("problemtypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[2]));
		view.addObject("questionsids", questionsids);
		return view;
	}
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/questions/list");
		view.addObject("degofdifftypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("problemtypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[2]));
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public Map query(final KsQuestions questions) {
		Map m = success();
		m.put("message", questionsService.queryList(questions));
		return m;
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view) {
		view.addObject("degofdifftypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("questionstypejson", new Gson().toJson(dictService.getDictTypeSelectByCode(Constants.DICT_CODE[1])));
		view.addObject("problemtypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[2]));
		view.setViewName("manager/questions/add");
		return view;
	}
	

	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,Integer id) {
		view.addObject("degofdifftypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("questionstypejson", new Gson().toJson(dictService.getDictTypeSelectByCode(Constants.DICT_CODE[1])));
		view.addObject("problemtypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[2]));
		view.addObject("questions", questionsService.getById(id));
		view.setViewName("manager/questions/edit");
		return view;
	}
	
	@RequestMapping("info")
	public ModelAndView info(ModelAndView view,Integer id) {
		view.addObject("questions", questionsService.getInfoById(id));
		view.setViewName("manager/questions/info");
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public Map save(HttpServletRequest request,final KsQuestions questions){
		Map m = success();
		m.put("msg", questionsService.saveOrUpdate(questions));
		return m;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public Map update(HttpServletRequest request,final KsQuestions questions){
		Map m = success();
		m.put("msg", questionsService.saveOrUpdate(questions));
		return m;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete")
	public @ResponseBody Map delete(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", questionsService.delete(ids));
			}
		});
	}
	
}
