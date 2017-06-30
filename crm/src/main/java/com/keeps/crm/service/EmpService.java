package com.keeps.crm.service;

import java.util.List;
import java.util.Map;

import com.keeps.core.service.SoftService;
import com.keeps.model.TEmp;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: EmpService.java</p>  
 * <p>Description: 员工管理Service接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface EmpService extends SoftService{

	public Page queryList(TEmp emp);
	
	public List<TEmp> getList(TEmp emp);
	
	public TEmp getById(Integer id);
	
	public String updatePwdById(Integer id);
	
	public String updatePwd(String oldPwd,String newPwd,String checkNewPwd);
	
	public String saveOrUpdate(TEmp emp);
	
	public String delete(String ids);


	public String getFzridsByClientid(Integer clientid);
	
	public String updateFieidById(String fieid,Integer value,String ids);

	public List<TreeNode> getEmpTree(String empids);
	/**
	  * @Title:			getClientGroupByEmp 
	  * @Description:	根据员工分组查询员工客户量
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年6月24日
	 */
	public Map<String, Object> getClientGroupByEmp();
}
