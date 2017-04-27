package com.keeps.shengzhelai.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.shengzhelai.dao.AdvPositionDao;
import com.keeps.shengzhelai.service.AdvPositionService;

/** 
 * <p>Title: AdvPositionServiceImpl.java</p>  
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
public class AdvPositionServiceImpl extends AbstractService implements AdvPositionService{
	
	@Autowired
	private AdvPositionDao advPositionDao;
	
}
