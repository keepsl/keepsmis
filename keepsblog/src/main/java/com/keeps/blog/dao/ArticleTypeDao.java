package com.keeps.blog.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TArticleType;

/** 
 * <p>Title: ArticleTypeDao.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ArticleTypeDao extends SoftDao{

	/**
	  * @Title:			queryListMenu 
	  * @Description:	查询文章栏目
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月9日
	 */
	public List<TArticleType> queryListAll();
}
