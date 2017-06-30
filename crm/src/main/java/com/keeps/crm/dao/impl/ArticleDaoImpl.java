package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.ArticleDao;
import com.keeps.model.TArticle;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ArticleDaoImpl.java</p>  
 * <p>Description: 文章DAO实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class ArticleDaoImpl extends AbstractDao implements ArticleDao {
	
	/**
	 *	查询文章列表
	 */
	public Page queryList(TArticle article){
		StringBuffer sql = new StringBuffer(" select a.*,b.name as typename from t_article a inner join t_article_type b on a.typeid = b.id ");
		List<Object> values = new ArrayList<Object>();
		sql.append(" where isdelete = 1 ");
		if (article.getTypeid()!=null) {
			if (article.getTypeid()!=0) {
				sql.append(" and a.typeid = ? ");
				values.add(article.getTypeid());
			}
		}
		if (StringUtils.hasText(article.getTitle())) {
			sql.append(" and a.title like ? ");
			values.add("%"+article.getTitle().trim()+"%");
		}
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),article,article.getClass());
	}

	/**
	  * @Title:			updateFieidById 
	  * @Description:	根据id更新字段值
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	public Integer updateFieidById(String fieid,Integer value,String ids){
		String sql = "update t_article set "+fieid+"= :fieid where id in (:ids)";
		return super.executeSQL(sql, new String[]{"fieid","ids"}, new Object[]{value,ids});
	}

	@SuppressWarnings("rawtypes")
	public Integer getCountByTypeids(String typeids){
		String sql = "select count(1) from t_article a where isdelete = 1 and a.typeid in (:typeids)";
		List list = super.getByNameParamSql(sql, new String[]{"typeids"}, new Object[]{typeids});
		return Integer.parseInt(list.get(0).toString());
	}

}
