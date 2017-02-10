package com.keeps.blog.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TArticle;

public interface ArticleDao extends SoftDao{
	
	/**
	  * @Title:			queryTopRecommendList 
	  * @Description:	查询6条推荐文章
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月9日
	 */
	public List<TArticle> queryTopRecommendList();

	/**
	  * @Title:			queryTopNewList 
	  * @Description:	查询20条最新文章
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月9日
	 */
	public List<TArticle> queryTopNewList();
  

	/**
	  * @Title:			queryTopHotList 
	  * @Description:	查询前6条热门文章
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月9日
	 */
	public List<TArticle> queryTopHotList();
}
