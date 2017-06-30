package com.keeps.crm.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TRole;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: RoleDao.java</p>  
 * <p>Description: 角色DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface RoleDao extends SoftDao{

	public Page queryList(TRole role);
	
	public List<TRole> getList(TRole role);
	
	public List<TreeNode> getListTree();
	
	public Integer deleteEmproleByRoleids(String roleids);
	
	public Integer deleteMenuOperateAccessByRoleids(String roleids);

	public List<TRole> getListByEmpid(Integer empid);

}
