package com.keeps.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.manage.dao.ArticleTypeDao;
import com.keeps.utils.TreeNode;
import com.keeps.model.TArticleType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ArticleTypeDaoImpl.java</p>  
 * <p>Description: 文章类型DAO接口实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class ArticleTypeDaoImpl extends AbstractDao implements ArticleTypeDao {

	/**
	 * 获得文章栏目列表
	 */
	@Override
	public Page queryList(TArticleType articleType) {
		StringBuffer sql = new StringBuffer(" select * from t_article_type ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasLength(articleType.getName())) {
			sql.append(" where name like ? ");
			values.add("%"+articleType.getName().trim()+"%");
		}
		if (articleType.getPid()!=null) {
			if (articleType.getPid()!=0) {
				sql.append(" where pid = ? or id = ?");
				values.add(articleType.getPid());
				values.add(articleType.getPid());
			}
		}
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),articleType,articleType.getClass());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TArticleType> queryListAll(){
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,name,pid from t_article_type ");
		List<Object> values = new ArrayList<Object>();
		sb.append("order by sort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),TArticleType.class);
	}

	/**
	 * 取得树
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryListTree(){
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,pid as pId,name as name from t_article_type ");
		List<Object> values = new ArrayList<Object>();
		sb.append("order by sort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),TreeNode.class);
	}

}
