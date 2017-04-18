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

	public Page queryListByClassid(SzlGoods goods);
	
	public List<SzlGoods> getListByIds(String ids);

	/**
	  * @Title:			queryGuessLike 
	  * @Description:	猜测喜欢商品
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月18日
	 */
	public List<SzlGoods> getGoodsByGuessLike(Long goodsid,Integer pclassid,Integer classid);
	
	public Page queryList(SzlGoods goods);
	
	public SzlGoods getById(Long id);
}
