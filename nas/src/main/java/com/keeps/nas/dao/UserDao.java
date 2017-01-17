package com.keeps.nas.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TUser;

/**
 * 
 * <p>Title: UserDao.java</p>  
 * <p>Description: 用户DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2016年12月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface UserDao extends SoftDao{

	/**
	  * @Title:			getUserinfoByLoginid 
	  * @Description:	登录时查询用户
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月11日
	 */
	public TUser getUserinfoByLoginid(String loginid);
	
}
