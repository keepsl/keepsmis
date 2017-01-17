package com.keeps.nas.service;

import com.keeps.core.service.SoftService;
import com.keeps.model.TUser;

/**
 * 
 * <p>Title: UserService.java</p>  
 * <p>Description: 用户登录SERVICE接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2016年12月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface UserService extends SoftService{

	/**
	  * @Title:			keepLogin 
	  * @Description:	用户登录并保存COOKIE
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月11日
	 */
	public TUser keepLogin(String loginid,String password,String code, String cookscaptcha);
}
