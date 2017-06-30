package com.keeps.crm.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TLogOperation;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: LogOperationDao.java</p>  
 * <p>Description: 操作日志DAO接口 </p>  
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
	
	/**
	  * @Title:			queryList 
	  * @Description:	查询操作日志列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月5日
	 */
	public Page queryList(TLogOperation logOperation);
}
