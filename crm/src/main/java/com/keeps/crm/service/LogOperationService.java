package com.keeps.crm.service;

import com.keeps.core.service.SoftService;
import com.keeps.model.TLogOperation;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: LogOperationService.java</p>  
 * <p>Description: 操作日志SERVICE接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface LogOperationService extends SoftService{

	/**
	  * @Title:			queryList 
	  * @Description:	查询日志列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月5日
	 */
	public Page queryList(TLogOperation logOperation);
}
