package com.keeps.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.manage.dao.AdvDao;
import com.keeps.model.TAdv;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class AdvDaoImpl extends AbstractDao implements AdvDao {


	@Override
	public Page queryList(TAdv adv) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" select a.* from t_adv a  ");
		if (StringUtils.hasText(adv.getAdvTitle())) {
			sql.append(" where a.adv_title = ? ");
			values.add(adv.getAdvTitle().trim());
		}
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),adv,adv.getClass());
	}

	@SuppressWarnings("unchecked")
	public List<TAdv> queryAll(){
		StringBuffer sql = new StringBuffer(" select a.* from szl_tag a ");
		return super.getByPropertySql(sql.toString(), null, TAdv.class);
	}
	
	@Override
	public Integer updateFieidById(String fieid, Integer value, String ids) {
		String sql = "update szl_tag set "+fieid+"= :fieid where id in (:ids)";
		return super.executeSQL(sql, new String[]{"fieid","ids"}, new Object[]{value,ids});
	}
	

}
