package com.keeps.crm.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.model.TOrg;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: OrgSerivce.java</p>  
 * <p>Description: 组织SERVICE接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface OrgService extends SoftService{

	public Page queryList(TOrg org);
	
	public List<TOrg> getList(TOrg org);
	
	public TOrg getById(Integer id);
	
	public String saveOrUpdate(TOrg org);
	
	public String delete(String ids);
	
	public List<TreeNode> getOrgTree();
}
