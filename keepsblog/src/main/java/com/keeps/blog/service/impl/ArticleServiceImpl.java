package com.keeps.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.blog.dao.ArticleDao;
import com.keeps.blog.service.ArticleService;
import com.keeps.core.service.AbstractService;
import com.keeps.model.TArticle;

/** 
 * <p>Title: ArticleServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ArticleServiceImpl extends AbstractService implements ArticleService {
	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public List<TArticle> queryTopRecommendList() {
		return articleDao.queryTopRecommendList();
	}

	@Override
	public List<TArticle> queryTopNewList() {
		return articleDao.queryTopNewList();
	}

	@Override
	public List<TArticle> queryTopHotList() {
		return articleDao.queryTopHotList();
	}
	
}
