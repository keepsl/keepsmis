package com.keeps.shengzhelai.dao;

import java.util.List;

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
	
	/**
	  * @Title:			getTopListByHot 
	  * @Description:	查询热门商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月18日
	 */
	public List<SzlGoods> getTopListByHot(Integer rows);

	/**
	  * @Title:			getTopListByRecommend 
	  * @Description:	查询推荐商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月18日
	 */
	public List<SzlGoods> getTopListByRecommend(Integer rows);

	/**
	  * @Title:			queryListByClassid 
	  * @Description:	根据商品分类查询商品列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月18日
	 */
	public List<SzlGoods> getTopListByClassid(Long goodsid,Integer classid,Integer rows);
	
	/**
	  * @Title:			queryListByPclassid 
	  * @Description:	根据商品大分类查询商品列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月18日
	 */
	public List<SzlGoods> getTopListByPclassid(Long goodsid,Integer pclassid,Integer classid,Integer rows);
	/**
	  * @Title:			getListByIds 
	  * @Description:	根据商品id查询商品列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月18日
	 */
	public List<SzlGoods> getListByIds(String ids);
}
