package com.keeps.crm.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.LogOperationDao;
import com.keeps.model.TLogOperation;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: LogOperationDaoImpl.java</p>  
 * <p>Description: 操作日志DAO接口实现类 </p>  
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
	
	/**
	 * 查询操作日志列表
	 */
	public Page queryList(TLogOperation logOperation){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" select a.*,b.name as nickname from t_log_operation a left join t_emp b on a.userid = b.id ");
		if (StringUtils.hasText(logOperation.getMethod())) {
			sql.append(" where a.method = ? ");
			values.add(logOperation.getMethod().trim());
		}
		sql.append("order by a.id desc ");
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),logOperation,logOperation.getClass());
	}

}
