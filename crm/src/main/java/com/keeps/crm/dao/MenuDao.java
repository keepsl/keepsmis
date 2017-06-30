package com.keeps.crm.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.utils.TreeNode;
import com.keeps.model.TManagerMenu;
import com.keeps.model.TOperateMethod;
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
	

	public Integer countUrlByRole(Integer userid,String url);
	
	public TManagerMenu getById(Integer id);
	/**
	  * @Title:			queryListAll
	  * @Description:	查询所有节点
	  * @param:			status,pid
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	public List<TManagerMenu> queryListAll(Integer status,Integer pid);
	
	public List<TManagerMenu> queryListByUserRole(Integer empid);
	
	/**
	  * @Title:			getListByPid 
	  * @Description:	根据PID取得菜单列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	public List<TManagerMenu> getListByPid(Integer pid);

	/**
	  * @Title:			queryListAll 
	  * @Description:	查询菜单tree
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月20日
	 */
	public List<TreeNode> queryListTreeAll();
	
	public List<TreeNode> queryListTreeByUserRole(Integer empid);

	public Integer deleteOperateMethodByCode(Integer menuid,String code);
	
	public Integer deleteOperateMethodByMenuid(Integer menuid);

	public List<TOperateMethod> getOperateMethodList(String menuids);
	
	public Integer countGrantMenuByMenuid(Integer roleid,Integer menuid);
	
	public Integer countGrantOperateByOperateid(Integer roleid,Integer operateid);
	
	public Integer countOperateByCode(String code);
	
	public Integer updateOperateByCode(String name,String code);

	public Integer deleteMenuAccess(Integer relid,Integer type);
	
	public Integer deleteOperateAccess(Integer relid,Integer type);
	
	public Integer deleteMenuOperateAccessByMenuids(String menuids);
}
