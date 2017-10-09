package com.keeps.ksxtmanager.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.ksxtmanager.model.KsQuestions;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: QuestionsDao.java</p>  
 * <p>Description: 试题管理 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年9月17日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface QuestionsDao extends SoftDao{

	/**
	  * @Title:			queryList 
	  * @Description:	查询试题列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年9月17日
	 */
	public Page queryList(KsQuestions questions);
	
	public KsQuestions getInfoById(Integer id);
}
