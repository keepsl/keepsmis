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
import com.keeps.ksxtmanager.model.KsShijuan;
import com.keeps.ksxtmanager.service.ShijuanService;
import com.keeps.ksxtmanager.system.service.DictService;
import com.keeps.ksxtmanager.utils.Constants;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: ShijuanController.java</p>  
 * <p>Description: 试卷管理控制类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年10月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RestController
@RequestMapping("shijuan")
public class ShijuanController extends AbstractAPIController{

	@Autowired
	private ShijuanService shijuanService;

	@Autowired
	private DictService dictService;
	
	/**
	  * @Title:			index 
	  * @Description:	初始化打开试卷列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年10月5日
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response){
		view.setViewName("manager/shijuan/list");
		view.addObject("degofdifftypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public Map query(final KsShijuan shijuan) {
		Map m = success();
		m.put("message", shijuanService.queryList(shijuan));
		return m;
	}
	
	/**
	  * @Title:			querywksj 
	  * @Description:	我的考试-待考
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年10月7日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("queryWksj")
	public Map queryWksj(final KsShijuan shijuan) {
		Map m = success();
		m.put("message", shijuanService.querywksjList(shijuan));
		return m;
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view) {
		view.addObject("degofdifftypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("shijuantypejson", new Gson().toJson(dictService.getDictTypeSelectByCode(Constants.DICT_CODE[3])));
		view.setViewName("manager/shijuan/add");
		return view;
	}
	

	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,Integer id) {
		view.addObject("degofdifftypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("shijuantypejson", new Gson().toJson(dictService.getDictTypeSelectByCode(Constants.DICT_CODE[3])));
		view.addObject("shijuan", shijuanService.getById(id));
		view.setViewName("manager/shijuan/edit");
		return view;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public Map save(HttpServletRequest request,final KsShijuan shijuan){
		Map m = success();
		m.put("msg", shijuanService.saveOrUpdate(shijuan));
		return m;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public Map update(HttpServletRequest request,final KsShijuan shijuan){
		Map m = success();
		m.put("msg", shijuanService.saveOrUpdate(shijuan));
		return m;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete")
	public @ResponseBody Map delete(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", shijuanService.delete(ids));
			}
		});
	}
	
}
