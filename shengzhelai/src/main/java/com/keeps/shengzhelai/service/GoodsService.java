package com.keeps.shengzhelai.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.model.SzlGoods;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsService.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface GoodsService extends SoftService{

	/**
	  * @Title:			queryListByPrice 
	  * @Description:	根据价格查询商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月22日
	 */
	public Page queryListByPrice(SzlGoods goods);

	/**
	  * @Title:			queryListByClassid 
	  * @Description:	根据分类查询商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月22日
	 */
	public Page queryListByClassid(SzlGoods goods);
	
	/**
	  * @Title:			queryHomeList 
	  * @Description:	首页查询商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月22日
	 */
	public Page queryHomeList(SzlGoods goods);
	
	/**
	  * @Title:			getListByIds 
	  * @Description:	根据多个商品ID查询商品列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月22日
	 */
	public List<SzlGoods> getListByIds(String ids);

	/**
	  * @Title:			getTopListByRecommend 
	  * @Description:	 推荐商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月19日
	 */
	public List<SzlGoods> getTopListByRecommend(Integer rows);
	
	/**
	  * @Title:			updateGoodsById 
	  * @Description:	更新商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月22日
	 */
	public String updateGoodsById(SzlGoods goods);

	/**
	  * @Title:			queryGuessLike 
	  * @Description:	猜测喜欢商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月18日
	 */
	public List<SzlGoods> getGoodsByGuessLike(Long goodsid,Integer pclassid,Integer classid);
	
	/**
	  * @Title:			getById 
	  * @Description:	根据商品id获得商品信息
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月22日
	 */
	public SzlGoods getById(Long id);
}
