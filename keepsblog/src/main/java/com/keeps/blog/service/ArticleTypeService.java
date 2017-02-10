package com.keeps.blog.service;


import com.keeps.core.service.SoftService;
import com.keeps.tools.utils.AsyncTreeNode;

/** 
 * <p>Title: ArticleTypeService.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ArticleTypeService extends SoftService{

	/**
	  * @Title:			queryListMenu 
	  * @Description:	获取文章分类树
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月9日
	 */
	public AsyncTreeNode queryListMenu();
}
