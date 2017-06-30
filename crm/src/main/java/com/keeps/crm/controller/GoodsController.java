package com.keeps.crm.controller;

import java.util.HashMap;
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

import com.google.gson.Gson;
import com.keeps.core.controller.AbstractController;
import com.keeps.crm.service.GoodsClassService;
import com.keeps.crm.service.GoodsService;
import com.keeps.crm.service.TagService;
import com.keeps.crm.service.impl.GoodsServiceImpl;
import com.keeps.model.SzlGoods;
import com.keeps.model.TTag;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.JsonPost;
import com.keeps.utils.Constants;
import com.keeps.utils.FileUtil;

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
	
	@RequestMapping("progressbar")
	public @ResponseBody String progressbar(HttpServletRequest request,Integer flag) {
		Map<Object, Object> map= new HashMap<>();
		if (flag==1) {
			GoodsServiceImpl.uplength = 0;
			GoodsServiceImpl.upcontent = "正在上传...";
		}
		map.put("uplength", GoodsServiceImpl.uplength);
		map.put("upcontent", GoodsServiceImpl.upcontent);
		return new Gson().toJson(map);
	}
	
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/goods/list");
		view.addObject("websiteUrl", Constants.websiteUrl);
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
	
	@RequestMapping("analysis")
	public ModelAndView analysis(ModelAndView view,Integer classid,Integer pclassid){
		view.setViewName("manager/goods/analysis");
		view.addObject("classid", classid);
		view.addObject("pclassid", pclassid);
		view.addObject("goodsclasslist", goodsClassService.queryListAll(-1));
		return view;
	}
	@RequestMapping("imp")
	public ModelAndView imp(ModelAndView view){
		view.setViewName("manager/goods/imp");
		return view;
	}
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer classid,Integer pclassid) {
		view.setViewName("manager/goods/add");
		view.addObject("classid", classid);
		view.addObject("pclassid",pclassid);
		SzlGoods goods = new SzlGoods();
		goods.setStarttime(DateUtils.format(DateUtils.getNow(), "yyyy-MM-dd HH:mm:ss"));
		view.addObject("goods", goods);
		view.addObject("goodsclasslist", goodsClassService.queryListAll(-1));
		List<TTag> taglist = tagService.queryAll();
		view.addObject("taglist",taglist);
		return view;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Long id) {
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
	  * @Title:			saveAnalysisToCopywriter 
	  * @Description:	保存解析商品文案
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月30日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("saveAnalysisToCopywriter")
	public @ResponseBody Map saveAnalysisToCopywriter(HttpServletRequest request,final Integer classid,final Integer pclassid,final Integer goodssource,final String copywriter){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", goodsService.saveAnalysisToCopywriter(classid,pclassid,goodssource,copywriter));
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
	
	/**
	  * @Title:			downloadTemplate 
	  * @Description:	下载模版
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月16日
	 */
	@RequestMapping("download")
	public void downloadTemplate(HttpServletRequest request,HttpServletResponse response){
		String filepath = request.getSession().getServletContext().getRealPath("/skins/template/download/goods_template.xlsx");
		String fileName = "商品信息导入模版.xls";
		FileUtil.downloadFile(filepath, fileName,response);
	}
	
	/**
	  * @Title:			importCourseFile 
	  * @Description:	批量导入商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月16日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("imp/file")
	public @ResponseBody Map impGodos(final @RequestParam(value = "goodsfile", required = false) MultipartFile goodsfile,final HttpServletRequest request, final HttpServletResponse response){
		Map map = super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message",goodsService.saveGoodsfileData(goodsfile, request));
			}
		});
        return map;
	}
	
}
