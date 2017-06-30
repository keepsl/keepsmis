package com.keeps.crm.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.service.SoftService;
import com.keeps.model.TArticle;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ArticleService.java</p>  
 * <p>Description: 文章SERVICE接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ArticleService extends SoftService{

	/**
	  * @Title:			queryList
	  * @Description:	查询文章列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public Page queryList(TArticle article);
	
	/**
	  * @Title:			getById 
	  * @Description:	根据ID获得一条文章信息
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public TArticle getById(Integer id,HttpServletRequest request);
	
	/**
	  * @Title:			saveOrUpdate 
	  * @Description:	发布、更新文章
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public String saveOrUpdate(TArticle article,MultipartFile coverfile, HttpServletRequest request);
	
	/**
	  * @Title:			updateFieidById 
	  * @Description:	根据id更新字段值
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月8日
	 */
	public String updateFieidById(String fieid,Integer value,String ids);
}
