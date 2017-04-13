package com.keeps.shengzhelai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.model.SzlGoods;
import com.keeps.shengzhelai.service.GoodsClassService;
import com.keeps.shengzhelai.service.GoodsService;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.Constants;

/** 
 * <p>Title: IndexPage.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("page")
public class IndexPageCntroller extends AbstractController {
	
	@Autowired
	private GoodsClassService goodsClassService;
	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("page/index");
		view.addObject("goodsclasslist",goodsClassService.getGoodsClassList(0));
		Page page = goodsService.queryHotList(new SzlGoods());
		view.addObject("viewpath", Constants.file_view_path+"/"+Constants.GOODS_COVER_IMAGE_PATH);
		view.addObject("goodslist", page);
		return view;
	}
	
}
