package com.keeps.manage.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TAdv;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvDao.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface AdvDao extends SoftDao{

	public Page queryList(TAdv adv);

	public List<TAdv> queryAll();
	
	public Integer updateFieidById(String fieid,Integer value,String ids);

	public Integer getCountByApId(Integer apId);

	public List<TAdv> getAdvByApId(Integer apId);

	public List<TAdv> getGroupApidListByIds(String ids);

}
