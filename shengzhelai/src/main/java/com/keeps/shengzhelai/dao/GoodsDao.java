package com.keeps.shengzhelai.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.SzlGoods;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsDao.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface GoodsDao extends SoftDao{
	
	/**
	  * @Title:			queryList 
	  * @Description:	分页查询商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月8日
	 */
	public Page queryList(SzlGoods goods);
}
