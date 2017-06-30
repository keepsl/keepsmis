package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.GoodsDao;
import com.keeps.model.SzlGoods;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class GoodsDaoImpl extends AbstractDao implements GoodsDao {

	@Override
	public Page queryList(SzlGoods goods) {
		StringBuffer sql = new StringBuffer(" select a.*,b.classname as classname from szl_goods a  ");
		sql.append(" inner join szl_goods_class b on a.classid = b.id ");
		List<Object> values = new ArrayList<Object>();
		sql.append(" where isdelete = 1 ");
		if (goods.getClassid()!=null) {
			if (goods.getClassid()!=0) {
				sql.append(" and (a.classid = ? or a.pclassid = ?) ");
				values.add(goods.getClassid());
				values.add(goods.getClassid());
			}
		}
		if (StringUtils.hasText(goods.getGoodsname())) {
			sql.append(" and a.goodsname like ? ");
			values.add("%"+goods.getGoodsname().trim()+"%");
		}
		if (goods.getGoodssource()!=null && goods.getGoodssource() != 0) {
			sql.append(" and a.goodssource = ? ");
			values.add(goods.getGoodssource());
		}
		if (goods.getIshot()!=null && goods.getIshot() != 0) {
			sql.append(" and a.ishot = ? ");
			values.add(goods.getIshot());
		}
		if (goods.getIsrecommend()!=null && goods.getIsrecommend() != 0) {
			sql.append(" and a.isrecommend = ? ");
			values.add(goods.getIsrecommend());
		}
		if (goods.getSearchtype()!=null) {
			if (goods.getSearchtype()==1) {
				sql.append(" and a.endtime >= ? ");
				values.add(DateUtils.getNow("yyyy-MM-dd"));
			}else if(goods.getSearchtype()==2){
				sql.append(" and a.endtime < ? ");
				values.add(DateUtils.getNow("yyyy-MM-dd"));
			}
		}
		sql.append(" order by a.updatetime desc ");
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),goods,goods.getClass());
	
	}

	@Override
	public Integer updateFieidById(String fieid, Integer value, String ids) {
		String sql = "update szl_goods set "+fieid+"= :fieid where id in (:ids)";
		return super.executeSQL(sql, new String[]{"fieid","ids"}, new Object[]{value,ids.split(",")});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer getCountByGoodsClassIds(String classids) {
		String sql = "select count(1) from szl_goods a where a.isdelete = 1 and a.classid in (:classids)";
		List list = super.getByNameParamSql(sql, new String[]{"classids"}, new Object[]{classids});
		return Integer.parseInt(list.get(0).toString());
	
	}
	
	@SuppressWarnings("rawtypes")
	public Integer getCountByGoodsname(String goodsname){
		String sql = "select count(1) from szl_goods a where a.isdelete = 1 and a.goodsname = (:goodsname)";
		List list = super.getByNameParamSql(sql, new String[]{"goodsname"}, new Object[]{goodsname});
		return Integer.parseInt(list.get(0).toString());
	}
	
	
}
