package com.keeps.nas.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.model.TUser;
import com.keeps.nas.dao.UserDao;
import com.keeps.nas.service.UserService;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.MD5Util;

/**
 * 
 * <p>Title: UserServiceImpl.java</p>  
 * <p>Description: 用户登录SERVICE实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2016年12月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service("userService")
public class UserServiceImpl extends AbstractService implements UserService {
	@Autowired 
	private UserDao userDao;
	/**
	 * 用户登录并保存COOKIE
	 */
	@Override
	public TUser keepLogin(String loginid, String password,String code, String cookscaptcha) {

		if (StringUtils.isBlank(loginid)) {
			throw new CapecException("用户名不能为空!");
		}
		if (StringUtils.isBlank(password)) {
			throw new CapecException("密码不能为空!");
		}
	/*	if (StringUtils.isBlank(code)) {
			throw new CapecException("验证码不能为空!");
		}
		if(!code.toUpperCase().equals(cookscaptcha)){
			throw new CapecException("验证码输入错误!");
		}*/
		TUser user = userDao.getUserinfoByLoginid(loginid);
		if (user == null) {
			throw new CapecException("用户名、密码错误!");
		}
		String md5pwd = MD5Util.getInstance().toMD5(password).toUpperCase();
		if (!md5pwd.equals(user.getPassword())) {
			throw new CapecException("用户名、密码错误!");
		}
		return user;
	}
	
	
	
}
