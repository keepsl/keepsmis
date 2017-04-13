package com.keeps.shengzhelai.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.model.SzlGoodsClass;

/** 
 * <p>Title: GoodsClassService.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface GoodsClassService extends SoftService{
	
	
	public List<SzlGoodsClass> getGoodsClassList(Integer pid);
	
	public SzlGoodsClass getById(Integer id);

}
