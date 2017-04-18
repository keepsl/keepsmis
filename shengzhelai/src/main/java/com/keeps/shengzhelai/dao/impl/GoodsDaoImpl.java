package com.keeps.shengzhelai.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.model.SzlGoods;
import com.keeps.shengzhelai.dao.GoodsDao;
import com.keeps.tools.utils.DateUtils;
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
		sb.append("select * from szl_goods a where isdelete=1 ");
		if (goods.getClassid()!=null) {
			sb.append(" and a.classid = ? ");
			values.add(goods.getClassid());
		}
		if (goods.getPclassid()!=null) {
			sb.append(" and a.pclassid = ? ");
			values.add(goods.getPclassid());
		}
		sb.append(" and a.endtime >= ? ");
		values.add(DateUtils.getNow("yyyy-MM-dd"));
		sb.append(" order by a.updatetime  ");
		return super.queryBySql(sb.toString(), values.toArray(), goods, goods.getClass());
	}
	
	public List<SzlGoods> getTopListByHot(Integer rows){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(" select a.* from szl_goods a ");
		sb.append("  where isdelete=1 and a.endtime >= ? ");
		values.add(DateUtils.getNow("yyyy-MM-dd"));
		sb.append("  and a.ishot = ? order by a.updatetime desc ");
		return super.getByPropertySqlTop(sb.toString(), values.toArray(), rows ,SzlGoods.class);
	}
	
	public List<SzlGoods> getTopListByRecommend(Integer rows){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(" select a.* from szl_goods a ");
		sb.append("  where isdelete=1 and a.endtime >= ? ");
		values.add(DateUtils.getNow("yyyy-MM-dd"));
		sb.append("  and a.isrecommend = ? order by a.updatetime desc ");
		return super.getByPropertySqlTop(sb.toString(), values.toArray(), rows ,SzlGoods.class);
	}

	public List<SzlGoods> getTopListByClassid(Long goodsid,Integer classid,Integer rows){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(" select a.* from szl_goods a ");
		sb.append("  where isdelete=1 and a.endtime >= ? ");
		values.add(DateUtils.getNow("yyyy-MM-dd"));
		sb.append("  and a.classid = ?  ");
		values.add(classid);
		if (goodsid != null) {
			sb.append("  and a.id <> ? ");
			values.add(goodsid);
		}
		sb.append("  order by a.updatetime desc ");
		return super.getByPropertySqlTop(sb.toString(), values.toArray(), rows ,SzlGoods.class);
	}

	public List<SzlGoods> getTopListByPclassid(Long goodsid,Integer pclassid,Integer classid,Integer rows){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(" select a.* from szl_goods a ");
		sb.append("  where isdelete=1 and a.endtime >= ? ");
		values.add(DateUtils.getNow("yyyy-MM-dd"));
		sb.append("  and a.pclassid = ? ");
		values.add(pclassid);
		if (classid != null) {
			sb.append("  and a.classid <> ?  ");
			values.add(classid);
		}
		if (goodsid != null) {
			sb.append("  and a.id <> ? ");
			values.add(goodsid);
		}
		sb.append("  order by a.updatetime desc ");
		return super.getByPropertySqlTop(sb.toString(), values.toArray(), rows, SzlGoods.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<SzlGoods> getListByIds(String ids){
		StringBuffer sb = new StringBuffer(" select a.* from szl_goods a ");
		sb.append("  where a.id in (:ids) ");
		return super.getByNameParamSql(sb.toString(), new String[] { "ids" }, new Object[] { ids.split(",") }, SzlGoods.class);
	}

	
}
