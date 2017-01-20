package com.keeps.manage.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.manage.utils.LayuiTree;
import com.keeps.model.TManagerMenu;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: MenuDao.java</p>  
 * <p>Description: 菜单SERVICE接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface MenuService extends SoftService{

	/**
	  * @Title:			save 
	  * @Description:	保存菜单
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	public String save(TManagerMenu menu);
	
	/**
	  * @Title:			update 
	  * @Description:	修改菜单
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	public String update(TManagerMenu menu);
	
	/**
	  * @Title:			getById 
	  * @Description:	根据ID获得一条菜单信息
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	public TManagerMenu getById(Integer id);
	
	/**
	  * @Title:			queryListAll 
	  * @Description:	查询菜单列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	public List<TManagerMenu> queryListAll(Integer status,Integer pid);
	
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
	  * @Title:			delete 
	  * @Description:	删除菜单
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	public String delete(String ids);
	
	/**
	  * @Title:			getMenuTree 
	  * @Description:	首页获得菜单数
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月17日
	 */
	public LayuiTree getIndexMenuTree();
	
	/**
	  * @Title:			getMenuTree 
	  * @Description:	获得菜单树
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	public List<LayuiTree> getMenuTree();
	
}
