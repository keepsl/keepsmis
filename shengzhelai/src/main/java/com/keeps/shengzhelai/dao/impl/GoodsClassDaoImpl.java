package com.keeps.shengzhelai.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.model.SzlGoodsClass;
import com.keeps.shengzhelai.dao.GoodsClassDao;

/** 
 * <p>Title: GoodsClassDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class GoodsClassDaoImpl extends AbstractDao implements GoodsClassDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SzlGoodsClass> getGoodsClassList(Integer pid) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,classname,pid,classicon from szl_goods_class ");
		List<Object> values = new ArrayList<Object>();
		sb.append(" where pid = ? ");
		values.add(pid);
		sb.append("order by classsort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),SzlGoodsClass.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SzlGoodsClass getById(Integer id){
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from szl_goods_class where id = ? ");
		List<Object> values = new ArrayList<Object>();
		values.add(id);
		List<SzlGoodsClass> list = super.getByPropertySql(sb.toString(),values.toArray(),SzlGoodsClass.class);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}


}
