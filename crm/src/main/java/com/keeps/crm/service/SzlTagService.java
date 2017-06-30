package com.keeps.crm.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.model.SzlTag;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: SzlTagService.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月7日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface SzlTagService extends SoftService{

	public Page queryList(SzlTag tag);
	
	public List<SzlTag> queryAll();
	
	public SzlTag getById(Integer id);
	
	public String saveOrUpdate(SzlTag tag);
	
	public String updateFieidById(String fieid,Integer value,String ids);

	public String delete(String ids);

}
