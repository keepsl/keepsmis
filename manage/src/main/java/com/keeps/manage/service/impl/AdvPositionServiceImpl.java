package com.keeps.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.AdvPositionDao;
import com.keeps.manage.service.AdvPositionService;
import com.keeps.model.TAdvPosition;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvPositionServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class AdvPositionServiceImpl extends AbstractService implements AdvPositionService {

	@Autowired
	private AdvPositionDao advPositionDao;
	

	@Override
	public Page queryList(TAdvPosition advPosition) {
		return advPositionDao.queryList(advPosition);
	}

	@Override
	public List<TAdvPosition> queryAll() {
		return advPositionDao.queryAll();
	}

	@Override
	public TAdvPosition getById(Integer id) {
		return super.get(TAdvPosition.class, id);
	}

	@Override
	public String saveOrUpdate(TAdvPosition advPosition) {
		Assert.isTrue(StringUtils.hasText(advPosition.getApName()), "标签名称不能为空!");
		return null;
	}

	@Override
	public String updateFieidById(String fieid, Integer value, String ids) {
		advPositionDao.updateFieidById(fieid, value, ids);
		return "更新成功!";
	}
	
	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(TAdvPosition.class, ids, IdTypes.Integer);
		return "删除成功!";
	}

}
