package com.keeps.manage.dao.impl;


import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.manage.dao.LogOperationDao;
import com.keeps.model.TLogOperation;

/** 
 * <p>Title: LogOperationDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository("logOperationDao")
public class LogOperationDaoImpl extends AbstractDao implements LogOperationDao {

	@Override
	public int saveLog(TLogOperation logOperation) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into t_log_operation (method, message, ip, userid, createtime) values ( ?, ?, ?, ?, ?) ");
		Object[] values = new Object[]{logOperation.getMethod(),logOperation.getMessage(),logOperation.getIp(),logOperation.getUserid(),logOperation.getCreatetime()};
		return super.executeSQL(sql.toString(), values);
	}
	
}
