package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.TagDao;
import com.keeps.model.TTag;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: TagDaoImpl.java</p>  
 * <p>Description: 标签DAO实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class TagDaoImpl extends AbstractDao implements TagDao {

	@Override
	public Page queryList(TTag tag) {
		StringBuffer sql = new StringBuffer(" select a.*,b.name as typename from t_tag a inner join t_article_type b on a.typeid = b.id ");
		List<Object> values = new ArrayList<Object>();
		sql.append(" where 1 = 1 ");
		if (tag.getTypeid()!=null) {
			if (tag.getTypeid()!=0) {
				sql.append(" and a.typeid = ? ");
				values.add(tag.getTypeid());
			}
		}
		if (StringUtils.hasText(tag.getName())) {
			sql.append(" and a.name = ? ");
			values.add(tag.getName().trim());
		}
		
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),tag,tag.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public List<TTag> queryAll(){
		StringBuffer sql = new StringBuffer(" select a.* from t_tag a ");
		List<TTag> list = super.getByPropertySql(sql.toString(), null, TTag.class);
		return list;
	}
	
}
