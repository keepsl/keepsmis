package com.keeps.crm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.crm.service.GoodsClassService;
import com.keeps.model.SzlGoodsClass;
import com.keeps.tools.utils.JsonPost;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsClassController.java</p>  
 * <p>Description: 商品分类管理 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("goodsClass")
public class GoodsClassController extends AbstractController{
	
	@Autowired
	private GoodsClassService goodsClassService;
	
	/**
	  * @Title:			index 
	  * @Description:	商品分类首页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月5日
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/goodsCLass/list");
		return view;
	}
	
	/**
	  * @Title:			query 
	  * @Description:	查询商品分类列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月5日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final SzlGoodsClass goods_class) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				Page page = goodsClassService.queryList(goods_class);
				map.put("message", page);
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getGoodsClassTree")
	public @ResponseBody Map getGoodsClassTree(){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsClassService.queryListTree());
			}
		});
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer pid) {
		view.setViewName("manager/goodsCLass/add");
		view.addObject("pid",pid);
		view.addObject("goodsclasslist", goodsClassService.queryListAll(-1));
		return view;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/goodsCLass/edit");
		view.addObject("goodsClass", goodsClassService.getById(id));
		view.addObject("goodsclasslist", goodsClassService.queryListAll(-1));
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public @ResponseBody Map save(final SzlGoodsClass goodsClass){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsClassService.save(goodsClass));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public @ResponseBody Map update(final SzlGoodsClass goodsClass){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsClassService.update(goodsClass));
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
				arg0.put("message", goodsClassService.delete(ids));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIshotById")
	public @ResponseBody Map updateIshotById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsClassService.updateFieidById("ishot",value,id));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIsrecommendById")
	public @ResponseBody Map updateIsrecommendById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsClassService.updateFieidById("isrecommend",value,id));
			}
		});
	}
	
}
