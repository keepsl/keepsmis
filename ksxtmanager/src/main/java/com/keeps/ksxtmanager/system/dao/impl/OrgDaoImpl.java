package com.keeps.ksxtmanager.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.ksxtmanager.system.dao.OrgDao;
import com.keeps.model.TOrg;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: OrgDaoImpl.java</p>  
 * <p>Description: 组织DAO接口实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class OrgDaoImpl extends AbstractDao implements OrgDao {

	@Override
	public Page queryList(TOrg org) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_org where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(org.getName())) {
			sb.append(" and name like ? ");
			values.add("%"+org.getName().trim()+"%");
		}
		if (org.getPid()!=null && org.getPid()!=0) {
			sb.append(" and pid = ?  or id = ? ");
			values.add(org.getPid());
			values.add(org.getPid());
		}
		if(StringUtils.hasText(org.getSidx()) && StringUtils.hasText(org.getSord())) {
        	sb.append("order by "+org.getSidx()+" "+org.getSord());
		}else{
			sb.append("  order by id desc ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), org, TOrg.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TOrg> getList(TOrg org) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_org where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(org.getName())) {
			sb.append(" and name like ? ");
			values.add("%"+org.getName().trim()+"%");
		}
		sb.append("  order by name ");
		return super.getByPropertySql(sb.toString(), values.toArray(), TOrg.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreeNode> getListTree() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,pid as pId,name from t_org ");
		sb.append("order by name ");
		return super.getByPropertySql(sb.toString(),null,TreeNode.class);
	}

	@SuppressWarnings("rawtypes")
	public Integer countByIds(String ids){
		List list = super.getByNameParamSql(" select count(1) from t_org where pid in (:ids) ", new String[]{"ids"}, new Object[]{ids.split(",")});
		return Integer.parseInt(list.get(0).toString());
	}
	
}
