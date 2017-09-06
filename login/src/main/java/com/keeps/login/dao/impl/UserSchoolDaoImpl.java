package com.keeps.login.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.login.dao.UserSchoolDao;
import com.keeps.tools.utils.UserSchool;

@Repository
public class UserSchoolDaoImpl extends AbstractDao implements UserSchoolDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public UserSchool getUserSchool(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id as userid,jobnumber as loginid,name as nickname,email,phone,isadmin from t_emp where id=? and status = 1 ");
		List<UserSchool> list = super.getByPropertySql(sb.toString(), new Object[] { id }, UserSchool.class);
		return CollectionUtils.isEmpty(list) ? new UserSchool() : list.get(0);
	}

	@Override
	public Integer countUrlByRole(Integer userid,String url) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) from ( ");
		sql.append(" select a.id,d.menuname as name,d.url from t_emprole a inner join t_role b on a.roleid = b.id ");
		sql.append(" inner join t_menu_access c on b.id = c.relid and c.type = 1 ");
		sql.append(" inner join t_manager_menu d on c.menuid = d.id and d.status = 1 ");
		sql.append(" where a.empid = ? ");
		sql.append(" union ");
		sql.append(" select a.id,f.name as mothod,f.code from t_emprole a inner join t_role b on a.roleid = b.id ");
		sql.append(" inner join t_operate_access e on b.id = e.relid and e.type = 1 ");
		sql.append(" inner join t_operate_method f on e.operateid = f.id ");
		sql.append(" where a.empid = ? ) a where a.url = ? ");

		List list = super.getByPropertySql(sql.toString(), new Object[]{userid,userid,url});
		return (CollectionUtils.isEmpty(list)) ? 0:Integer.parseInt(list.get(0).toString());
	}
	
	public Integer countMethodUrlByUrl(String code){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) from t_operate_method where code = ? ");
		List list = super.getByPropertySql(sql.toString(), new Object[]{code});
		return (CollectionUtils.isEmpty(list)) ? 0:Integer.parseInt(list.get(0).toString());
	}

	public Integer countMenuUrlByUrl(String url){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) from t_manager_menu where url = ? ");
		List list = super.getByPropertySql(sql.toString(), new Object[]{url});
		return (CollectionUtils.isEmpty(list)) ? 0:Integer.parseInt(list.get(0).toString());
	}

	
}
