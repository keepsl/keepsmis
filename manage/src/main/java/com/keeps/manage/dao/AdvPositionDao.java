package com.keeps.manage.dao;

import java.util.List;

import com.keeps.model.TAdvPosition;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvPositionDao.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface AdvPositionDao {

	public Page queryList(TAdvPosition advPosition);

	public List<TAdvPosition> queryAll();
	
	public Integer updateFieidById(String fieid,Integer value,String ids);


}
