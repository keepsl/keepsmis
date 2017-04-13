package com.keeps.blog.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.keeps.core.service.SoftService;
import com.keeps.model.TArticle;

/** 
 * <p>Title: ArticleService.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ArticleService extends SoftService{
	  
	public List<TArticle> queryTopRecommendList(HttpServletRequest request);
	  
	public List<TArticle> queryTopNewList(HttpServletRequest request);
	  
	public List<TArticle> queryTopHotList(HttpServletRequest request);
	  
	public TArticle getById(Integer id,HttpServletRequest request);
}
