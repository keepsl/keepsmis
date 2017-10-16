package com.keeps.crm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractAPIController;
import com.keeps.core.controller.AbstractController;
import com.keeps.crm.service.DictService;
import com.keeps.model.TClient;
import com.keeps.model.TDict;
import com.keeps.model.TDictType;
import com.keeps.tools.utils.JsonPost;
import com.keeps.utils.Constants;

/** 
 * <p>Title: DictController.java</p>  
 * <p>Description: 字典控制类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("dict")
public class DictController extends AbstractAPIController {
	@Autowired
	private DictService dictService;
	
	
	@RequestMapping("productSelectWin")
	public ModelAndView productSelectWin(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/dict/dictSelectWin");
		view.addObject("code", Constants.DICT_CODE[1]);
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("getDictTree")
	public @ResponseBody Map getDictTree(String code) {
		Map m = success();
		m.put("recored", dictService.getDictTree(code));
		return m;
	}
	
	
	/**
	  * @Title:			index 
	  * @Description:	字典管理首页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月19日
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/dict/list");
		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("queryDict")
	public @ResponseBody Map queryDict(final TDict dict) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", dictService.queryDictList(dict));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getDicttypeTree")
	public @ResponseBody Map getDicttypeTree(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", dictService.getDictTypeTree());
			}
		});
	}
	

	@RequestMapping("addDict")
	public ModelAndView addDict(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer typeid) {
		view.addObject("dicttype", dictService.geTDictTypeById(typeid));
		view.setViewName("manager/dict/addDict");
		return view;
	}
	
	@RequestMapping("addDictType")
	public ModelAndView addDictType(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.addObject("pid", id);
		/*TDictType dictType = dictService.geTDictTypeById(id);
		view.addObject("pname", dictType.getName());*/
		view.addObject("dicttypelist", dictService.getDictTypeList(new TDictType()));
		view.addObject("pdicttype", dictService.geTDictTypeById(id));
		view.setViewName("manager/dict/addDictType");
		return view;
	}
	
	@RequestMapping("editDict")
	public ModelAndView editDict(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.addObject("dict", dictService.getDictById(id));
		view.setViewName("manager/dict/editDict");
		return view;
	}
	

	@RequestMapping("editDictType")
	public ModelAndView editDictType(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.addObject("dicttypelist", dictService.getDictTypeList(new TDictType()));
		view.addObject("dicttype", dictService.geTDictTypeById(id));
		view.setViewName("manager/dict/editDictType");
		return view;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("saveDict")
	public @ResponseBody Map saveDict(HttpServletRequest request,final TDict dict){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", dictService.saveOrUpdateDict(dict));
			}
		});
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("updateDict")
	public @ResponseBody Map updateDict(HttpServletRequest request,final TDict dict){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", dictService.saveOrUpdateDict(dict));
			}
		});
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("saveDictType")
	public @ResponseBody Map saveDict(HttpServletRequest request,final TDictType dictType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", dictService.saveOrUpdateDictType(dictType));
			}
		});
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("updateDictType")
	public @ResponseBody Map updateDict(HttpServletRequest request,final TDictType dictType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", dictService.saveOrUpdateDictType(dictType));
			}
		});
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("deleteDictById")
	public @ResponseBody Map deleteDictById(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", dictService.deleteDictById(ids));
			}
		});
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping("deleteDictTypeById")
	public @ResponseBody Map deleteDictTypeById(final Integer id){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", dictService.deleteDictTypeById(id));
			}
		});
	}
}
