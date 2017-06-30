package com.keeps.crm.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.model.TTag;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: TagService.java</p>  
 * <p>Description: 标签Service接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface TagService extends SoftService{

	/**
	  * @Title:			queryList 
	  * @Description:	标签列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public Page queryList(TTag tag);
	
	/**
	  * @Title:			query 
	  * @Description:	获得标签列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月7日
	 */
	public List<TTag> queryAll();
	/**
	  * @Title:			getById 
	  * @Description:	根据ID查询标签信息
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public TTag getById(Integer id);
	
	/**
	  * @Title:			save 
	  * @Description:	保存标签
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public String save(TTag tag);
	
	/**
	  * @Title:			update 
	  * @Description:	更新标签
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public String update(TTag tag);
	
	/**
	  * @Title:			delete 
	  * @Description:	删除标签
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年2月6日
	 */
	public String delete(String ids);
}
