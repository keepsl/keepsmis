package com.keeps.manage.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.SzlGoods;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: Goods.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface GoodsDao extends SoftDao{

	public Page queryList(SzlGoods goods);
	
	public Integer updateFieidById(String fieid,Integer value,String ids);
	
	public Integer getCountByGoodsClassIds(String classids);

	public Integer getCountByGoodsname(String goodsname);
}
