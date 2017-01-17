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
		sb.append(" select id as userid,nickname,email,phone from t_user where id=? and status = 1 ");
		List<UserSchool> list = super.getByPropertySql(sb.toString(), new Object[] { id }, UserSchool.class);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
}
