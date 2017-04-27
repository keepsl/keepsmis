package com.keeps.shengzhelai.dao;

import java.util.List;

import com.keeps.model.TAdvPosition;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvPositionDao.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface AdvPositionDao {

	/**
	  * @Title:			queryByApClass 
	  * @Description:	根据广告类别找到一条广告位
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月14日
	 */
	public TAdvPosition getByApClass(Integer apClass);
	
}
