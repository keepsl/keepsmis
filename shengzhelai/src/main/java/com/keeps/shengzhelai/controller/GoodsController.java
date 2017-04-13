package com.keeps.shengzhelai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.model.SzlGoods;
import com.keeps.model.SzlGoodsClass;
import com.keeps.shengzhelai.service.GoodsClassService;
import com.keeps.shengzhelai.service.GoodsService;
import com.keeps.utils.Constants;

/** 
 * <p>Title: GoodsController.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日
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
	
	@RequestMapping(value="info/{id}")
	public ModelAndView info(ModelAndView view,HttpServletRequest request, HttpServletResponse response,@PathVariable("id")Integer id) {
		view.setViewName("page/goodsinfo");
		SzlGoods goods = goodsService.getById(id);
		view.addObject("goods", goods);
		view.addObject("classid", goodsClassService.getById(goods.getClassid()).getId());
		view.addObject("pclassid", goodsClassService.getById(goods.getClassid()).getId());
		view.addObject("classname", goodsClassService.getById(goods.getClassid()).getClassname());
		view.addObject("pclassname", goodsClassService.getById(goods.getPclassid()).getClassname());
		view.addObject("viewpath", Constants.file_view_path+"/"+Constants.GOODS_COVER_IMAGE_PATH);
		return view;
	}
	

	@RequestMapping(value="list/{classid}")
	public ModelAndView goodslistByClassid(ModelAndView view,HttpServletRequest request, HttpServletResponse response,@PathVariable("classid")Integer classid) {
		if (classid==null) {
			view.setViewName("page/index");
		}else{
			view.setViewName("page/goodslist");
			SzlGoods goods = new SzlGoods();
			SzlGoodsClass szlGoodsClass = goodsClassService.getById(classid);
			if (szlGoodsClass.getPid()==0) {//当前选中节点是父分类
				goods.setPclassid(szlGoodsClass.getId());
				view.addObject("pclassid",szlGoodsClass.getId());
				//查询选中父分类的子分类
				view.addObject("classlist", goodsClassService.getGoodsClassList(szlGoodsClass.getId()));
			}else{//选中的是子分类
				goods.setClassid(szlGoodsClass.getId());
				goods.setPclassid(szlGoodsClass.getPid());
				view.addObject("classid",szlGoodsClass.getId());
				view.addObject("pclassid",szlGoodsClass.getPid());
				//查询选中父分类的子分类
				view.addObject("classlist", goodsClassService.getGoodsClassList(szlGoodsClass.getPid()));
			}
			//查询父分类列表
			view.addObject("pclasslist", goodsClassService.getGoodsClassList(0));
			//商品列表
			view.addObject("goodslist", goodsService.queryListByClassid(goods));
			view.addObject("viewpath", Constants.file_view_path+"/"+Constants.GOODS_COVER_IMAGE_PATH);
		}
		return view;
	}
}
