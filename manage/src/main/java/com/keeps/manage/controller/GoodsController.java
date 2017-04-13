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
import com.keeps.manage.service.GoodsClassService;
import com.keeps.manage.service.GoodsService;
import com.keeps.manage.service.TagService;
import com.keeps.model.SzlGoods;
import com.keeps.model.TTag;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.JsonPost;

/** 
 * <p>Title: GoodsController.java</p>  
 * <p>Description: 商品管理 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("goods")
public class GoodsController extends AbstractController{
	
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsClassService goodsClassService;
	@Autowired
	private TagService tagService;
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/goods/list");
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final SzlGoods goods) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", goodsService.queryList(goods));
			}
		});
	}
	
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer classid) {
		view.setViewName("manager/goods/add");
		view.addObject("classid", classid);
		SzlGoods goods = new SzlGoods();
		goods.setStarttime(DateUtils.format(DateUtils.getNow(), "yyyy-MM-dd HH:mm:ss"));
		view.addObject("goods", goods);
		view.addObject("goodsclasslist", goodsClassService.queryListAll(-1));
		List<TTag> taglist = tagService.queryAll();
		view.addObject("taglist",taglist);
		return view;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("manager/goods/edit");
		view.addObject("goods",goodsService.getById(id,request));
		view.addObject("goodsclasslist", goodsClassService.queryListAll(-1));
		List<TTag> taglist = tagService.queryAll();
		view.addObject("taglist",taglist);
		return view;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("saveOrUpdate")
	public @ResponseBody Map saveOrUpdate(HttpServletRequest request,final SzlGoods goods
			,final @RequestParam(value = "goodsimagefile", required = false) MultipartFile goodsimagefile
			,final @RequestParam(value = "qrcodepathfile", required = false) MultipartFile qrcodepathfile){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsService.saveOrUpdate(goods,goodsimagefile,qrcodepathfile,request));
			}
		});
	}
	
	/**
	  * @Title:			delete 
	  * @Description:	逻辑删除文章
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete")
	public @ResponseBody Map delete(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsService.updateFieidById("isdelete",2,ids));
			}
		});
	}
	
	
	/**
	  * @Title:			updateIshotById 
	  * @Description:	是否热门
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIshotById")
	public @ResponseBody Map updateIshotById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsService.updateFieidById("ishot",value,id));
			}
		});
	}
	
	/**
	  * @Title:			updateIshotById 
	  * @Description:	是否推荐
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("updateIsrecommendById")
	public @ResponseBody Map updateIsrecommendById(final String id,final Integer value){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsService.updateFieidById("isrecommend",value,id));
			}
		});
	}
	
}
