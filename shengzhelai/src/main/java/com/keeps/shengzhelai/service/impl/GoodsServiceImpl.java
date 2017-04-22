package com.keeps.shengzhelai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.model.SzlGoods;
import com.keeps.shengzhelai.dao.GoodsDao;
import com.keeps.shengzhelai.service.GoodsService;
import com.keeps.tools.utils.EditType;
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
	
	public Page queryListByPrice(SzlGoods goods){
		//TODO 后续业务处理
		return goodsDao.queryList(goods);
	}

	
	public Page queryListByClassid(SzlGoods goods){
		//TODO 后续业务处理
		Page page = goodsDao.queryList(goods);
		return page;
	}
	
	public Page queryHomeList(SzlGoods goods){
		//TODO 后续业务处理
		return goodsDao.queryList(goods);
	}

	public List<SzlGoods> getListByIds(String ids){
		return goodsDao.getListByIds(ids);
	}

	/**
	  * @Title:			getTopListByRecommend 
	  * @Description:
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月19日
	 */
	public List<SzlGoods> getTopListByRecommend(Integer rows){
		return goodsDao.getTopListByRecommend(rows);
	}
	
	public String updateGoodsById(SzlGoods goods){
		super.update(goods, EditType.NULL_UN_UPDATE);
		return null;
	}

	/**
	 * 猜测喜欢商品
	 */
	public List<SzlGoods> getGoodsByGuessLike(Long goodsid,Integer pclassid,Integer classid){
		//空，说明没有找到商品详细信息，随机找到商品推荐
		List<SzlGoods> goodslist = null;
		if (classid==null) {
			goodslist = goodsDao.getTopListByRecommend(20);
			List<SzlGoods> goodslist2 = goodsDao.getTopListByHot(20);
			for (SzlGoods szlGoods : goodslist2) {
				goodslist.add(szlGoods);
			}
		}else{
			//根据子分类查询80条商品信息
			goodslist = goodsDao.getTopListByClassid(goodsid,classid,80);
			//查询出的商品过于少
			if (goodslist.size()<10) {
				//根据大分类在查询商品
				List<SzlGoods> goodslist2 = goodsDao.getTopListByPclassid(goodsid,pclassid,classid,70);
				for (SzlGoods szlGoods : goodslist2) {
					goodslist.add(szlGoods);
				}
			}
		}
		return goodslist;
	}

	public SzlGoods getById(Long id){
		SzlGoods goods = super.get(SzlGoods.class, id);
		return goods;
	}

}
