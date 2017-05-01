package com.keeps.shengzhelai.controller;

import java.util.List;
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
import com.keeps.model.SzlGoods;
import com.keeps.model.SzlGoodsClass;
import com.keeps.shengzhelai.service.GoodsClassService;
import com.keeps.shengzhelai.service.GoodsService;
import com.keeps.shengzhelai.utils.GoodsCookieUtils;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.CookieUtils;
import com.keeps.tools.utils.JsonPost;
import com.keeps.tools.utils.StringUtils;
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
	
	/**
	  * @Title:			search 
	  * @Description:	首页搜索
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月24日
	 */
	@RequestMapping(value="search")
	public ModelAndView search(ModelAndView view,HttpServletRequest request, HttpServletResponse response,String keywords) {
		if (keywords==null) {
			view.setViewName("page/index");
		}else{
			view.setViewName("page/goods_classid_list");
			SzlGoods goods = new SzlGoods();
			//查询父分类列表
			view.addObject("pclasslist", goodsClassService.getGoodsClassList(0));
			//商品列表
			goods.setKeywords(keywords);
			goods.setPage(1);
			goods.setRows(Constants.goods_page_rows);
			view.addObject("goodslist", goodsService.queryListByClassid(goods));
			view.addObject("viewgoodspath", Constants.file_view_path+"/"+Constants.GOODS_COVER_IMAGE_PATH);
		}
		return view;
	}

	/**
	  * @Title:			history 
	  * @Description:	查询浏览历史
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月17日
	 */
	@RequestMapping(value="history")
	public ModelAndView history(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		//取得cookie中的商品ids
		String cookievalue = GoodsCookieUtils.getGoodsids(request);
		List<SzlGoods> list = null;
		if (StringUtils.hasText(cookievalue)) {
			//获得缓存中的商品
			list = goodsService.getListByIds(cookievalue.trim());
		}else{
			//缓存中没有，找猜你喜欢商品
			list = goodsService.getGoodsByGuessLike(null,null,null);
		}
		view.addObject("historyList",list);
		view.addObject("viewgoodspath", Constants.file_view_path+"/"+Constants.GOODS_COVER_IMAGE_PATH);
		view.setViewName("page/goods_history_list");
		return view;
	}

	/**
	  * @Title:			goodsinfo 
	  * @Description:	商品详细信息
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月12日
	 */
	@RequestMapping(value="info/{id}")
	public ModelAndView goodsinfo(ModelAndView view,HttpServletRequest request, HttpServletResponse response,@PathVariable("id")Long id) {
		view.setViewName("page/goodsinfo");
		SzlGoods goods = goodsService.getById(id);
		if (goods!=null) {
			view.addObject("classid", goodsClassService.getById(goods.getClassid()).getId());
			view.addObject("pclassid", goodsClassService.getById(goods.getClassid()).getId());
			view.addObject("classname", goodsClassService.getById(goods.getClassid()).getClassname());
			view.addObject("pclassname", goodsClassService.getById(goods.getPclassid()).getClassname());
			view.addObject("viewgoodspath", Constants.file_view_path+"/"+Constants.GOODS_COVER_IMAGE_PATH);
			
			//商品详细
			view.addObject("goods", goods);
			//猜你喜欢商品列表,传入当前商品ID 不查询当前的商品
			view.addObject("guessLikelist", goodsService.getGoodsByGuessLike(id,goods.getPclassid(),goods.getClassid()));
			
			//获得缓存中的商品ID
			String cookievalue = GoodsCookieUtils.getGoodsids(request);
			if (StringUtils.hasText(cookievalue)) {
				//获得缓存中的商品信息
				List<SzlGoods> list = goodsService.getListByIds(CommonUtils.getSubStr(cookievalue.trim(), 12, ",").trim());
				view.addObject("historyList",list);
			}else{
				//缓存中没有去找12条推荐商品
				List<SzlGoods> list = goodsService.getTopListByRecommend(12);
				view.addObject("ishistory","1");
				view.addObject("historyList",list);
			}
			//本次浏览的商品存入缓存
			GoodsCookieUtils.setGoodsids(request, response, id.toString());
			
			String cvalue = CookieUtils.getCookieValue(request, "keeps_goods_clicknum");
			if (StringUtils.notText(cvalue)) {
				SzlGoods goodsUpclicknum = new SzlGoods();
				goodsUpclicknum.setId(goods.getId());
				goods.setClicknum(goods.getClicknum()+1);
				goodsService.updateGoodsById(goodsUpclicknum);
			}
			CookieUtils.setCookie(response, "keeps_goods_clicknum", "1", 60);
		}else{
			goodsService.getGoodsByGuessLike(null,null,null);
			view.setViewName("common/error");
		}
		return view;
	}
	

	/**
	  * @Title:			goodsinfo 
	  * @Description:	根据分类查找商品分页列表
	  * @param:	
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月12日
	 */
	@RequestMapping(value="list/{typeid}")
	public ModelAndView goodslistByClassid(ModelAndView view,HttpServletRequest request, HttpServletResponse response,@PathVariable("typeid")Integer typeid) {
		if (typeid==null) {
			view.setViewName("page/index");
		}else{
			view.setViewName("page/goods_classid_list");
			SzlGoods goods = new SzlGoods();
			SzlGoodsClass szlGoodsClass = goodsClassService.getById(typeid);
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
			goods.setPage(1);
			goods.setRows(Constants.goods_page_rows);
			view.addObject("goodslist", goodsService.queryListByClassid(goods));
			view.addObject("viewgoodspath", Constants.file_view_path+"/"+Constants.GOODS_COVER_IMAGE_PATH);
		}
		return view;
	}
	/**
	  * @Title:			findClassGoodsListJson 
	  * @Description:	根据分类查找商品分页列表  ajax请求
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月19日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="findClassGoodsListJson")
	public @ResponseBody Map findClassGoodsListJson(ModelAndView view,Integer page,Integer rows,Integer pclassid,String classid){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				SzlGoods goods = new SzlGoods();
				goods.setPage(page);
				goods.setRows(rows);
				goods.setPclassid(pclassid);
				if (StringUtils.hasText(classid)) {
					goods.setClassid(Integer.parseInt(classid));
				}
				map.put("message", goodsService.queryListByClassid(goods));
			}
		});
	}

	/**
	  * @Title:			homeGoodsList 
	  * @Description:	首页商品查询，根据发布时间，销量，热门等各方面查询
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月15日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="findHomeGoodsListJson")
	public @ResponseBody Map findHomeGoodsListJson(ModelAndView view,Integer page,Integer rows){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				SzlGoods goods = new SzlGoods();
				goods.setPage(page);
				goods.setRows(rows);
				map.put("message", goodsService.queryHomeList(goods));
			}
		});
	}
	
	/**
	  * @Title:			goodslistByClassid 
	  * @Description:	根据商品价格查找
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月22日
	 */
	@RequestMapping(value="{type}")
	public ModelAndView goodslistByPrice(ModelAndView view,HttpServletRequest request, HttpServletResponse response,@PathVariable("type")String type) {
		if (type==null) {
			view.setViewName("page/index");
		}else{
			view.setViewName("page/goods_list");
			SzlGoods goods = new SzlGoods();
			switch (type) {
				case "k9" :
					//查询9.9以下的商品
					goods.setSearchStartPrice(0F);
					goods.setSearchEndPrice(9.9F);
					break;
				case "k20" :
					//查询9.9到20之间的商品(不包含9.9)
					goods.setSearchStartPrice(9.9F);
					goods.setSearchEndPrice(20F);
					break;
				case "k49" :
					//查询20-49之间的商品(不包含20)
					goods.setSearchStartPrice(20F);
					goods.setSearchEndPrice(49F);
					break;
				default:
					break;
			}
			//商品列表
			goods.setPage(1);
			goods.setRows(Constants.goods_page_rows);
			view.addObject("goodslist", goodsService.queryListByPrice(goods));
			view.addObject("viewgoodspath", Constants.file_view_path+"/"+Constants.GOODS_COVER_IMAGE_PATH);
		}
		return view;
	}
}
