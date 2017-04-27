package com.keeps.shengzhelai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.model.SzlGoodsClass;
import com.keeps.shengzhelai.dao.GoodsClassDao;
import com.keeps.shengzhelai.service.GoodsClassService;

/** 
 * <p>Title: GoodsClassServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class GoodsClassServiceImpl extends AbstractService implements GoodsClassService{

	@Autowired
	private GoodsClassDao goodsClassDao;
	
	public List<SzlGoodsClass> getGoodsClassList(Integer pid){
		return goodsClassDao.getGoodsClassList(pid);
	}
	
	public SzlGoodsClass getById(Integer id){
		return goodsClassDao.getById(id);
	}


}
