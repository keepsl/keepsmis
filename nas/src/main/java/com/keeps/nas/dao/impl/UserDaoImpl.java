package com.keeps.nas.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.model.TUser;
import com.keeps.nas.dao.UserDao;

/**
 * 
 * <p>Title: UserDaoImpl.java</p>  
 * <p>Description: 用户DAO接口实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2016年12月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class UserDaoImpl extends AbstractDao implements UserDao{

	/**
	 * 登录时查询用户
	 */
	@Override
	public TUser getUserinfoByLoginid(String loginid) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_user where nickname = ? and status = 1 ");
		Object[] param = new Object[]{loginid};
		List<TUser> list = super.getByPropertySql(sb.toString(), param, TUser.class);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
	
}
