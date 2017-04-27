package com.keeps.shengzhelai.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TAdv;

/** 
 * <p>Title: AdvDao.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月14日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface AdvDao extends SoftDao {
	
	public List<TAdv> queryList(Integer apId);
}
