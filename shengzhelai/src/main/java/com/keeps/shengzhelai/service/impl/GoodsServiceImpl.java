package com.keeps.shengzhelai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.model.SzlGoods;
import com.keeps.shengzhelai.dao.GoodsDao;
import com.keeps.shengzhelai.service.GoodsService;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class GoodsServiceImpl extends AbstractService implements GoodsService {
	
	@Autowired
	private GoodsDao goodsDao;
	public Page queryListByClassid(SzlGoods goods){
		Page page = goodsDao.queryList(goods);
		return page;
	}
	
	public Page queryHotList(SzlGoods goods){
		return goodsDao.queryList(goods);
	}

	public SzlGoods getById(Integer id){
		SzlGoods goods = super.get(SzlGoods.class, id);
		return goods;
	}

}
