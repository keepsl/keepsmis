package com.keeps.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.TagDao;
import com.keeps.manage.service.TagService;
import com.keeps.model.TTag;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: TagServiceImpl.java</p>  
 * <p>Description: 标签Service实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class TagServiceImpl extends AbstractService implements TagService {
	
	@Autowired
	private TagDao tagDao;
	
	/**
	 * 查询标签列表
	 */
	public Page queryList(TTag tag){
		return tagDao.queryList(tag);
	}

	/**
	 * 获得标签列表
	 */
	public List<TTag> queryAll(){
		return tagDao.queryAll();
	}

	/**
	 * 根据ID查询标签信息
	 */
	public TTag getById(Integer id){
		return super.get(TTag.class, id);
	}

	@Override
	public String save(TTag tag) {
		Assert.isTrue(StringUtils.hasLength(tag.getName()), "标签名不能为空!");
		Assert.isTrue(tag.getTypeid()!=null, "栏目不能为空!");
		Assert.isTrue(tag.getIshot()!=null, "是否热门不能为空!");
		tag.setClicknum(0);
		super.save(tag);
		return null;
	}

	@Override
	public String update(TTag tag) {
		Assert.isTrue(tag.getId()!=null, "ID不能为空!");
		Assert.isTrue(StringUtils.hasLength(tag.getName()), "标签名不能为空!");
		Assert.isTrue(tag.getTypeid()!=null, "栏目不能为空!");
		Assert.isTrue(tag.getIshot()!=null, "是否热门不能为空!");
		super.update(tag, EditType.NULL_UN_UPDATE);
		return null;
	}

	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(TTag.class, ids, IdTypes.Integer);
		return "删除成功!";
	}

}
