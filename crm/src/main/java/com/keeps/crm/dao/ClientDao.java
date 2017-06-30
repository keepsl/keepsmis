package com.keeps.crm.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TClient;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ClientDao.java</p>  
 * <p>Description: 客户DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ClientDao extends SoftDao{

	public Page queryList(TClient client, Integer operType);
	
	public TClient getListById(Integer id);

	public List<TClient> getListByIds(String ids);

	public TClient getClientByPhone(String phone,Integer id);
	
	public Integer deleteEmpclientByClientids(String clientids);
	
	public Integer updateFieidById(String fieid, String value, String ids);
	
}
