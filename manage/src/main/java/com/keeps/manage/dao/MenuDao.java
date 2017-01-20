package com.keeps.manage.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TManagerMenu;
import com.keeps.tools.utils.page.Page;

/**
 * 
 * <p>Title: MenuDao.java</p>  
 * <p>Description: 菜单DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface MenuDao extends SoftDao{
	
	/**
	  * @Title:			queryList 	
	  * @Description:	获得菜单列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	public Page queryList(TManagerMenu menu);
	
	/**
	  * @Title:			queryListAll
	  * @Description:	查询所有节点
	  * @param:			status,pid
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	public List<TManagerMenu> queryListAll(Integer status,Integer pid);
}
