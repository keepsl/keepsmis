package com.keeps.shengzhelai.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.model.TAdv;
import com.keeps.shengzhelai.dao.AdvDao;

/** 
 * <p>Title: AdvDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月14日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class AdvDaoImpl extends AbstractDao implements AdvDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TAdv> queryList(Integer apId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_adv where ap_id = ? ");
		List<Object> values = new ArrayList<Object>();
		values.add(apId);
		List<TAdv> list = super.getByPropertySql(sb.toString(),values.toArray(),TAdv.class);
		return list;
		//return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

}
