package com.keeps.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.DictDao;
import com.keeps.manage.service.DictService;

/** 
 * <p>Title: DictServiceImpl.java</p>  
 * <p>Description: 字典SERVICE实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class DictServiceImpl extends AbstractService implements DictService {
	
	@Autowired
	private DictDao dictDao;
	
}
