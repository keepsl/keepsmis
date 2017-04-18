package com.keeps.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.manage.dao.GoodsClassDao;
import com.keeps.utils.TreeNode;
import com.keeps.model.SzlGoodsClass;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsClassDaoImpl.java</p>  
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
public class GoodsClassDaoImpl extends AbstractDao implements GoodsClassDao {

	@Override
	public Page queryList(SzlGoodsClass goodsClass) {
		StringBuffer sql = new StringBuffer(" select * from szl_goods_class ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasLength(goodsClass.getClassname())) {
			sql.append(" where classname like ? ");
			values.add("%"+goodsClass.getClassname().trim()+"%");
		}
		if (goodsClass.getPid()!=null) {
			if (goodsClass.getPid()!=0) {
				sql.append(" where pid = ? or id = ?");
				values.add(goodsClass.getPid());
				values.add(goodsClass.getPid());
			}
		}
		sql.append(" order by classsort ");
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),goodsClass,goodsClass.getClass());
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SzlGoodsClass> queryListAll(Integer pid) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,classname,pid from szl_goods_class ");
		List<Object> values = new ArrayList<Object>();
		if (pid==0) {
			sb.append(" where pid = 0 ");
		}else if(pid==1){
			sb.append(" where pid <> 0 ");
		}
		sb.append("order by classsort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),SzlGoodsClass.class);
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreeNode> queryListTree() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,pid as pId,classname as name from szl_goods_class ");
		List<Object> values = new ArrayList<Object>();
		sb.append("order by classsort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),TreeNode.class);
	
	}
	

	@Override
	public Integer updateFieidById(String fieid, Integer value, String ids) {
		String sql = "update szl_goods_class set "+fieid+"= :fieid where id in (:ids)";
		return super.executeSQL(sql, new String[]{"fieid","ids"}, new Object[]{value,ids});
	}
	
	@SuppressWarnings("unchecked")
	public SzlGoodsClass getByClassname(String classname){
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from szl_goods_class where classname=? ");
		List<Object> values = new ArrayList<Object>();
		values.add(classname);
		List<SzlGoodsClass> list = super.getByPropertySql(sb.toString(), values.toArray(), SzlGoodsClass.class);
		return CollectionUtils.isNotEmpty(list)?list.get(0):null;
	}

}
