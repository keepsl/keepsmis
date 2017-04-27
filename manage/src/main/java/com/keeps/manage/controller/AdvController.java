package com.keeps.manage.controller;

import java.util.List;
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
import com.keeps.manage.service.AdvService;
import com.keeps.model.TAdv;
import com.keeps.model.TAdvPosition;
import com.keeps.tools.utils.JsonPost;
import com.keeps.utils.Constants;

/** 
 * <p>Title: Adv.java</p>  
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
@RequestMapping("adv")
public class AdvController extends AbstractController {
	@Autowired
	private AdvService advService;
	@Autowired
	private AdvPositionService advPositionService;

	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/adv/list");
		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TAdv adv) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", advService.queryList(adv));
			}
		});
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer typeid) {
		List<TAdvPosition> listadvPosition = advPositionService.queryAll();
		view.addObject("listadvPosition", listadvPosition);
		TAdv adv = new TAdv();
		adv.setSlideSort(0);
		view.addObject("adv", adv);
		view.setViewName("manager/adv/add");
		return view;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/adv/edit");
		TAdv adv = advService.getById(id);
		view.addObject("adv", adv);
		view.addObject("advPosition", advPositionService.getById(adv.getApId()));
		view.addObject("viewpath", Constants.file_view_path+"/"+Constants.ADV_IMAGE_PATH);
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("saveOrUpdate")
	public @ResponseBody Map saveOrUpdate(HttpServletRequest request, final TAdv adv
			,final @RequestParam(value = "advcontentfile", required = false) MultipartFile advcontentfile){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", advService.saveOrUpdate(adv,advcontentfile,request));
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
				arg0.put("message", advService.updateFieidById("is_show",value,id));
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
				arg0.put("message", advService.delete(ids));
			}
		});
	}

	
}
