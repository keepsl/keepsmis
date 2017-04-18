package com.keeps.shengzhelai.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.model.TAdv;
import com.keeps.model.TAdvPosition;
import com.keeps.shengzhelai.dao.AdvDao;
import com.keeps.shengzhelai.dao.AdvPositionDao;
import com.keeps.shengzhelai.service.AdvService;

/** 
 * <p>Title: AdvServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月14日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class AdvServiceImpl extends AbstractService implements AdvService {

	@Autowired
	private AdvDao advDao;
	@Autowired
	private AdvPositionDao advPositionDao;
	
	public List<TAdv> getAdvByApClass(Integer apClass){
		TAdvPosition tAdvPosition = advPositionDao.getByApClass(apClass);
		
		return null;
	}

	
}
