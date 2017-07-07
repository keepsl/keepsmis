package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.NewsDao;
import com.keeps.model.TNews;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: NewsDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年7月1日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class NewsDaoImpl extends AbstractDao implements NewsDao {

	@Override
	public Page query(TNews news) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*, date_format(a.publishtime,'%Y-%m-%d %H:%i') as publishtimestr,b.name as dicttypename,c.name as empname from t_news a left join t_dict_type b on b.id = a.dicttype ");
		sb.append(" left join t_emp c on a.createperson = c.id ");
		sb.append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (news.getDicttype()!=null) {
			sb.append(" and a.dicttype=? ");
			values.add(news.getDicttype());
		}
		if (StringUtils.hasText(news.getTitle())) {
			sb.append(" and a.title like ? ");
			values.add("%"+news.getTitle().trim()+"%");
		}
		if (StringUtils.hasText(news.getPublishtimesta())) {//创建开始日
			sb.append(" and date_format(a.publishtime,'%Y-%m-%d') >= str_to_date(?,'%Y-%m-%d') ");
			values.add(news.getPublishtimesta().trim());
		}
		if (StringUtils.hasText(news.getPublishtimeend())) {//创建结束日
			sb.append(" and date_format(a.publishtime,'%Y-%m-%d') <= str_to_date(?,'%Y-%m-%d') ");
			values.add(news.getPublishtimeend().trim());
		}
		sb.append(" order by a.publishtime desc ");
		return super.queryBySql(sb.toString(), values.toArray(), news, TNews.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TNews getById(Integer id) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*, date_format(a.publishtime,'%Y-%m-%d %H:%i') as publishtimestr,b.name as dicttypename from t_news a left join t_dict_type b on b.id = a.dicttype ");
		sb.append(" where a.id = ? ");
		List<TNews> list = super.getByPropertySql(sb.toString(), new Object[]{id}, TNews.class);
		return (CollectionUtils.isEmpty(list)) ? null : list.get(0);
	
	}

}
