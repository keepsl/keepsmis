package com.keeps.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.ArticleTypeDao;
import com.keeps.manage.service.ArticleTypeService;

/** 
 * <p>Title: ArticleTypeServiceImpl.java</p>  
 * <p>Description: 文章分类SERVICE实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ArticleTypeServiceImpl extends AbstractService implements ArticleTypeService {

	@Autowired
	private ArticleTypeDao articleTypeDao;
	
}
