package com.keeps.manage.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TLogOperation;

/** 
 * <p>Title: LogOperationDao.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface LogOperationDao extends SoftDao{
	
	/**
	  * @Title:			saveLog 
	  * @Description:
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月16日
	 */
	public int saveLog(TLogOperation logOperation);
	
}
