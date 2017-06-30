package com.keeps.crm.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.model.TRole;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: RoleService.java</p>  
 * <p>Description: 角色SERVICE接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface RoleService extends SoftService{

	public Page queryList(TRole role);
	
	public List<TRole> getList(TRole role);
	
	public TRole getById(Integer id);
	
	public String saveOrUpdate(TRole role);
	
	public String delete(String ids);
	
	public List<TreeNode> getRoleTree(String roleids);
	
	public String getRolenamesByEmpid();

}
