package com.keeps.crm.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.utils.TreeNode;
import com.keeps.model.TArticleType;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ArticleTypeDao.java</p>  
 * <p>Description: 文章分类DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ArticleTypeDao extends SoftDao{

	/**
	  * @Title:			queryList 
	  * @Description:	获得文章栏目列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	public Page queryList(TArticleType articleType);
	
	/**
	  * @Title:			queryListAll 
	  * @Description:	取得所有文章栏目
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月5日
	 */
	public List<TArticleType> queryListAll();
	
	/**
	  * @Title:			queryListTree 
	  * @Description:	取得树
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	public List<TreeNode> queryListTree();
}
