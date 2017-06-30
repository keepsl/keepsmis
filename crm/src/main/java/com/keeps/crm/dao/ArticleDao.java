package com.keeps.crm.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TArticle;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ArticleDao.java</p>  
 * <p>Description: 文章DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ArticleDao extends SoftDao{
	
	/**
	  * @Title:			queryList 
	  * @Description:	查询文章列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public Page queryList(TArticle article);
	
	/**
	  * @Title:			updateFieidById 
	  * @Description:	根据id更新字段值
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	public Integer updateFieidById(String fieid,Integer value,String ids);
	
	/**
	  * @Title:			getCountById 
	  * @Description:	根据栏目IDcount文章数
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月9日
	 */
	public Integer getCountByTypeids(String typeids);
}