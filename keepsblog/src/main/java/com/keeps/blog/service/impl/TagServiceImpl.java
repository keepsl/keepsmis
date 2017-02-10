package com.keeps.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.blog.dao.TagDao;
import com.keeps.blog.service.TagService;
import com.keeps.core.service.AbstractService;
import com.keeps.model.TTag;

/** 
 * <p>Title: TagServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class TagServiceImpl extends AbstractService implements TagService {
	
	@Autowired
	private TagDao tagDao;
	
	@Override
	public List<TTag> queryTopHotList() {
		return tagDao.queryTopHotList();
	}
	
	
}
