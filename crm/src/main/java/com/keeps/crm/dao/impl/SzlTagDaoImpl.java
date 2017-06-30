package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.SzlTagDao;
import com.keeps.model.SzlTag;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: SzlTagDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月7日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class SzlTagDaoImpl extends AbstractDao implements SzlTagDao {

	@Override
	public Page queryList(SzlTag tag) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" select a.* from szl_tag a  ");
		if (StringUtils.hasText(tag.getTagname())) {
			sql.append(" where a.tagname = ? ");
			values.add(tag.getTagname().trim());
		}
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),tag,tag.getClass());
	}

	@SuppressWarnings("unchecked")
	public List<SzlTag> queryAll(){
		StringBuffer sql = new StringBuffer(" select a.* from szl_tag a ");
		return super.getByPropertySql(sql.toString(), null, SzlTag.class);
	}
	
	@Override
	public Integer updateFieidById(String fieid, Integer value, String ids) {
		String sql = "update szl_tag set "+fieid+"= :fieid where id in (:ids)";
		return super.executeSQL(sql, new String[]{"fieid","ids"}, new Object[]{value,ids});
	}
	
}
