package com.keeps.ksxtmanager.service;

import com.keeps.core.service.SoftService;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ExamService.java</p>  
 * <p>Description: 试卷管理SERVICE接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年10月4日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ExamService extends SoftService{

	/**
	  * @Title:			queryList 
	  * @Description:	查询试卷列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年10月4日
	 */
	public Page queryList();
	
}
