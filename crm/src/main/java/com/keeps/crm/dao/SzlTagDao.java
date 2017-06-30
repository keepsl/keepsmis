package com.keeps.crm.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.SzlTag;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: SzlTagDao.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月7日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface SzlTagDao extends SoftDao{

	public Page queryList(SzlTag tag);

	public List<SzlTag> queryAll();
	
	public Integer updateFieidById(String fieid,Integer value,String ids);

}
