package com.keeps.blog.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.model.TTag;

/** 
 * <p>Title: TagService.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface TagService extends SoftService{
	
	  public List<TTag> queryTopHotList();
	  
}
