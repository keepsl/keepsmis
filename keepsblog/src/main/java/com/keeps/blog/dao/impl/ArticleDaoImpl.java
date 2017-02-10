package com.keeps.blog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.blog.dao.ArticleDao;
import com.keeps.core.dao.AbstractDao;
import com.keeps.model.TArticle;

/** 
 * <p>Title: ArticleDaoImpl.java</p>  
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
public class ArticleDaoImpl extends AbstractDao implements ArticleDao {

	@Override
	public List<TArticle> queryTopRecommendList() {
		StringBuffer sql = new StringBuffer("select *,a.abstract as abstract_ from t_article a ");
		sql.append(" where a.isdelete = 1 and a.isshow = 1 and a.ispublish = 2 ");
		sql.append("   and a.isrecommend = 1 ");
		sql.append("   order by a.publishtime desc ");
		return super.getByPropertySqlTop(sql.toString(), null, 60, TArticle.class);
	}

	@Override
	public List<TArticle> queryTopNewList() {
		StringBuffer sql = new StringBuffer("select *,a.abstract as abstract_ from t_article a ");
		sql.append(" where a.isdelete = 1 and a.isshow = 1 and a.ispublish = 2 ");
		sql.append("   order by a.publishtime desc ");
		return super.getByPropertySqlTop(sql.toString(), null, 20, TArticle.class);
	}

	@Override
	public List<TArticle> queryTopHotList() {
		StringBuffer sql = new StringBuffer("select *,a.abstract as abstract_ from t_article a ");
		sql.append(" where a.isdelete = 1 and a.isshow = 1 and a.ispublish = 2 ");
		sql.append("   and a.ishot = 1 ");
		sql.append("   order by a.publishtime desc ");
		return super.getByPropertySqlTop(sql.toString(), null, 8, TArticle.class);
	}
	
}
