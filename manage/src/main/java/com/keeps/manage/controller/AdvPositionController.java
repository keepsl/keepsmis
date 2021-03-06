package com.keeps.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.manage.service.AdvPositionService;
import com.keeps.model.TAdvPosition;
import com.keeps.tools.utils.JsonPost;
import com.keeps.utils.Constants;

/** 
 * <p>Title: AdvPositionController.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("advPosition")
public class AdvPositionController extends AbstractController {

	@Autowired
	private AdvPositionService advPositionService;
	
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/advPosition/list");
		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TAdvPosition advPosition) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", advPositionService.queryList(advPosition));
			}
		});
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer typeid) {
		view.setViewName("manager/advPosition/add");
		return view;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/advPosition/edit");
		view.addObject("advPosition", advPositionService.getById(id));
		view.addObject("viewpath", Constants.file_view_path+"/"+Constants.ADV_POSITION_IMAGE_PATH);
		return view;
	}
	
	@RequestMapping("viewCode")
	public ModelAndView viewCode(ModelAndView view,Integer id){
		view.setViewName("manager/advPosition/viewCode");
		view.addObject("filepath", Constants.file_view_path+"/"+Constants.ADV_FILE_PATH+"/layout_adv_"+id+".js");
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("saveOrUpdate")
	public @ResponseBody Map saveOrUpdate(HttpServletRequest request, final TAdvPosition advPosition
			,final @RequestParam(value = "defaultcontentfile", required = false) MultipartFile defaultcontentfile){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", advPositionService.saveOrUpdate(advPosition,defaultcontentfile,request));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIsshowById")
	public @ResponseBody Map updateIsshowById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", advPositionService.updateFieidById("is_show",value,id));
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
				arg0.put("message", advPositionService.delete(ids));
			}
		});
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("cacheAdvFile")
	public @ResponseBody Map cacheAdvFile(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", advPositionService.cacheAdvFile(ids));
			}
		});
	}
	
	

}
