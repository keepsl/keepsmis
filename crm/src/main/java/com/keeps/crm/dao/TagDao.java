package com.keeps.crm.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TTag;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: TagDao.java</p>  
 * <p>Description: 标签DAO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface TagDao extends SoftDao{

	/**
	  * @Title:			queryList 
	  * @Description:	查询标签管理列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public Page queryList(TTag tag);
	
	/**
	  * @Title:			queryAll 
	  * @Description:	获得所有标签列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月7日
	 */
	public List<TTag> queryAll();
}
