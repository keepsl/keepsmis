package com.keeps.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.ArticleDao;
import com.keeps.manage.service.ArticleService;

/** 
 * <p>Title: ArticleServiceImpl.java</p>  
 * <p>Description: 文章SERVICE实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ArticleServiceImpl extends AbstractService implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	
}
