package com.keeps.shengzhelai.service;

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
	
	public Page queryHotList(SzlGoods goods);

	public SzlGoods getById(Integer id);
}
