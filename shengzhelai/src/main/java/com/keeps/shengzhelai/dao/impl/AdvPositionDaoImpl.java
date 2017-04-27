package com.keeps.shengzhelai.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.model.TAdvPosition;
import com.keeps.shengzhelai.dao.AdvPositionDao;

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

	@SuppressWarnings("unchecked")
	@Override
	public TAdvPosition getByApClass(Integer apClass) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_adv_position where ap_class = ? ");
		List<Object> values = new ArrayList<Object>();
		values.add(apClass);
		List<TAdvPosition> list = super.getByPropertySql(sb.toString(),values.toArray(),TAdvPosition.class);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

}
