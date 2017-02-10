package com.keeps.blog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.blog.dao.TagDao;
import com.keeps.core.dao.AbstractDao;
import com.keeps.model.TTag;

/** 
 * <p>Title: TagDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class TagDaoImpl extends AbstractDao implements TagDao {

	@Override
	public List<TTag> queryTopHotList() {
		StringBuffer sql = new StringBuffer("select * from t_tag where ishot = 1");
		return super.getByPropertySqlTop(sql.toString(), null, 30, TTag.class);
	}
	
}
