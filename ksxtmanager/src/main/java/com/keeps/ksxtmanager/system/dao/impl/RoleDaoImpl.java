package com.keeps.ksxtmanager.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.ksxtmanager.system.dao.RoleDao;
import com.keeps.model.TRole;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: RoleDaoImpl.java</p>  
 * <p>Description: 角色DAO接口实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class RoleDaoImpl extends AbstractDao implements RoleDao {

	@Override
	public Page queryList(TRole role) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_role where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(role.getName())) {
			sb.append(" and name like ? ");
			values.add("%"+role.getName().trim()+"%");
		}
		if (role.getStatus() != null) {
			sb.append(" and status = ? ");
			values.add(role.getStatus());
		}
		if(StringUtils.hasText(role.getSidx()) && StringUtils.hasText(role.getSord())) {
        	sb.append("order by "+role.getSidx()+" "+role.getSord());
		}else{
			sb.append("  order by id desc ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), role, TRole.class);
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TRole> getList(TRole role) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_role ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasLength(role.getName())) {
			sb.append(" where status = 1 and name like ? ");
			values.add("%"+role.getName().trim()+"%");
		}
		sb.append("  order by id desc ");
		return super.getByPropertySql(sb.toString(), values.toArray(), TRole.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TRole> getListByEmpid(Integer empid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.id,a.name from t_role a inner join t_emprole b on a.id = b.roleid and b.empid = ? ");
		sb.append("  order by a.name ");
		List<TRole> list = super.getByPropertySql(sb.toString(), new Object[]{empid}, TRole.class);
		return CollectionUtils.isEmpty(list)?null:list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreeNode> getListTree() {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,0 as pId,name from t_role where  status = 1 ");
		sb.append("order by name ");
		return super.getByPropertySql(sb.toString(),null,TreeNode.class);
	}
	
	public Integer deleteEmproleByRoleids(String roleids){
		return super.executeSQL("delete from t_emprole where roleid in (:roleids) ", new String[]{"roleids"} ,new Object[]{roleids.split(",")});
	}

	public Integer deleteMenuOperateAccessByRoleids(String roleids){
		super.executeSQL("delete from t_menu_access where relid in (:roleids) and type = 1 ", new String[]{"roleids"} ,new Object[]{roleids.split(",")});
		return super.executeSQL("delete from t_operate_access where relid in (:roleids) and type = 1 ", new String[]{"roleids"} ,new Object[]{roleids.split(",")});
	}

}
