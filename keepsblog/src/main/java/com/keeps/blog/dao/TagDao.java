package com.keeps.blog.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TTag;

public interface TagDao extends SoftDao{
	
  public abstract List<TTag> queryTopHotList();
  
}