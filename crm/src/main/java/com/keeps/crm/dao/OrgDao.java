package com.keeps.crm.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TOrg;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: OrgDao.java</p>  
 * <p>Description: 组织DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface OrgDao extends SoftDao{

	public Page queryList(TOrg org);
	
	public List<TOrg> getList(TOrg org);
	
	public List<TreeNode> getListTree();
	
	public Integer countByIds(String ids);
	
}
