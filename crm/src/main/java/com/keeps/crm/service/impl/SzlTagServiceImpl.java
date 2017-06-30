package com.keeps.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.crm.dao.SzlTagDao;
import com.keeps.crm.service.SzlTagService;
import com.keeps.model.SzlTag;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: SzlTagServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月7日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class SzlTagServiceImpl extends AbstractService implements SzlTagService {
	@Autowired
	private SzlTagDao szlTagDao;
	
	@Override
	public Page queryList(SzlTag tag) {
		return szlTagDao.queryList(tag);
	}

	@Override
	public List<SzlTag> queryAll() {
		return szlTagDao.queryAll();
	}

	@Override
	public SzlTag getById(Integer id) {
		return super.get(SzlTag.class, id);
	}

	@Override
	public String saveOrUpdate(SzlTag tag) {
		Assert.isTrue(StringUtils.hasText(tag.getTagname()), "标签名称不能为空!");
		if(!szlTagDao.isUnique(tag, new String[]{"tagname"})){
			throw new CapecException("["+tag.getTagname()+"]标签名称已经存在,不允许重复添加!");
		}
		if (tag.getId()==null) {
			if(tag.getClicknum()==null)tag.setClicknum(0);
			if(tag.getGoodsnum()==null)tag.setGoodsnum(0);
			if(tag.getIshot()==null)tag.setClicknum(1);
			super.save(tag);
		}else{
			super.update(tag, EditType.NULL_UN_UPDATE);
		}
		return null;
	}

	@Override
	public String updateFieidById(String fieid, Integer value, String ids) {
		szlTagDao.updateFieidById(fieid, value, ids);
		return "更新成功!";
	}
	
	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(SzlTag.class, ids, IdTypes.Integer);
		return "删除成功!";
	}

}
