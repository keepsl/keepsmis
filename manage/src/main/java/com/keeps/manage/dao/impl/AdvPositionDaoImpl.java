package com.keeps.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.manage.dao.AdvPositionDao;
import com.keeps.model.TAdvPosition;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvPositionDaoImpl.java</p>  
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
public class AdvPositionDaoImpl extends AbstractDao implements AdvPositionDao {

	@Override
	public Page queryList(TAdvPosition advPosition) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" select a.* from t_adv_position a  ");
		if (StringUtils.hasText(advPosition.getApName())) {
			sql.append(" where a.ap_name = ? ");
			values.add(advPosition.getApName().trim());
		}
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),advPosition,advPosition.getClass());
	}

	@SuppressWarnings("unchecked")
	public List<TAdvPosition> queryAll(){
		StringBuffer sql = new StringBuffer(" select a.* from t_adv_position a ");
		return super.getByPropertySql(sql.toString(), null, TAdvPosition.class);
	}
	
	@Override
	public Integer updateFieidById(String fieid, Integer value, String ids) {
		String sql = "update t_adv_position set "+fieid+"= :fieid where id in (:ids)";
		return super.executeSQL(sql, new String[]{"fieid","ids"}, new Object[]{value,ids});
	}
	

}
