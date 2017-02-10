package com.keeps.blog.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.blog.dao.ArticleTypeDao;
import com.keeps.core.dao.AbstractDao;
import com.keeps.model.TArticleType;

/** 
 * <p>Title: ArticleTypeDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class ArticleTypeDaoImpl extends AbstractDao implements ArticleTypeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TArticleType> queryListAll() {
		StringBuffer sql = new StringBuffer("select * from t_article_type a ");
		sql.append(" where a.isshow = 1 ");
		return super.getByPropertySql(sql.toString(), null, TArticleType.class);
	}
	
}
