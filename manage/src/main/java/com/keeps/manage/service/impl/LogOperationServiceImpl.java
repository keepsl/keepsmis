package com.keeps.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.LogOperationDao;
import com.keeps.manage.service.LogOperationService;
import com.keeps.model.TLogOperation;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: LogOperationService.java</p>  
 * <p>Description: 操作日志SERVICE实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class LogOperationServiceImpl extends AbstractService implements LogOperationService {
	
	@Autowired
	private LogOperationDao logOperationDao;

	/**
	 * 查询日志列表
	 */
	@Override
	public Page queryList(TLogOperation logOperation) {
		Page page = logOperationDao.queryList(logOperation);
		return page;
	}
}
