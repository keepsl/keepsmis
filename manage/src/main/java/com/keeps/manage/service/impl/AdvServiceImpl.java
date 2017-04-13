package com.keeps.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.AdvDao;
import com.keeps.manage.service.AdvService;
import com.keeps.model.TAdv;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvServiceImpl.java</p>  
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
public class AdvServiceImpl extends AbstractService implements AdvService {

	@Autowired
	private AdvDao advDao;

	@Override
	public Page queryList(TAdv adv) {
		return advDao.queryList(adv);
	}

	@Override
	public List<TAdv> queryAll() {
		return advDao.queryAll();
	}

	@Override
	public TAdv getById(Integer id) {
		return super.get(TAdv.class, id);
	}

	@Override
	public String saveOrUpdate(TAdv adv) {
		Assert.isTrue(StringUtils.hasText(adv.getAdvTitle()), "标签名称不能为空!");
		
		return null;
	}

	@Override
	public String updateFieidById(String fieid, Integer value, String ids) {
		advDao.updateFieidById(fieid, value, ids);
		return "更新成功!";
	}
	
	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(TAdv.class, ids, IdTypes.Integer);
		return "删除成功!";
	}

}
