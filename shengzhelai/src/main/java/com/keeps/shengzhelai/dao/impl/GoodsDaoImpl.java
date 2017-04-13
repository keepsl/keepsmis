package com.keeps.shengzhelai.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.model.SzlGoods;
import com.keeps.shengzhelai.dao.GoodsDao;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class GoodsDaoImpl extends AbstractDao implements GoodsDao {

	/**
	  * @Title:			queryTopList 
	  * @Description:	分页查询商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月8日
	 */
	@Override
	public Page queryList(SzlGoods goods) {
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from szl_goods a where 1=1 ");
		if (goods.getClassid()!=null) {
			sb.append(" and a.classid = ? ");
			values.add(goods.getClassid());
		}
		if (goods.getPclassid()!=null) {
			sb.append(" and a.pclassid = ? ");
			values.add(goods.getPclassid());
		}
		sb.append(" order by a.updatetime  ");
		return super.queryBySql(sb.toString(), values.toArray(), goods, goods.getClass());
	}
	
	
}
