package com.keeps.ksxtmanager.service;

import com.keeps.core.service.SoftService;
import com.keeps.ksxtmanager.model.KsQuestions;
import com.keeps.tools.utils.page.Page;
import com.sun.tools.hat.internal.server.QueryListener;

/** 
 * <p>Title: QuestionsService.java</p>  
 * <p>Description: 试题管理SERVICE接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年9月17日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface QuestionsService extends SoftService{

	/**
	  * @Title:			queryList 
	  * @Description:	查询试题列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年9月17日
	 */
	public Page queryList(KsQuestions questions);
	
	public String saveOrUpdate(KsQuestions questions);
	
	public KsQuestions getById(Integer id);
	
	
	public KsQuestions getInfoById(Integer id);

	public String delete(String ids);

}
