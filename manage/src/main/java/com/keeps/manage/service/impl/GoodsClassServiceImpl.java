package com.keeps.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.GoodsClassDao;
import com.keeps.manage.dao.GoodsDao;
import com.keeps.manage.service.GoodsClassService;
import com.keeps.utils.TreeNode;
import com.keeps.model.SzlGoodsClass;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsClassServiceImpl.java</p>  
 * <p>Description: 商品分类Service实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class GoodsClassServiceImpl extends AbstractService implements GoodsClassService {
	@Autowired
	private GoodsClassDao goodsClassDao;
	@Autowired
	private GoodsDao goodsDao;
	
	@Override
	public Page queryList(SzlGoodsClass goodsClass) {
		return goodsClassDao.queryList(goodsClass);
	}

	@Override
	public List<SzlGoodsClass> queryListAll(Integer pid) {
		return goodsClassDao.queryListAll(pid);
	}

	@Override
	public List<TreeNode> queryListTree() {
		TreeNode node  = new TreeNode();
		node.setId("0");
		node.setpId("0");
		node.setOpen(1);
		node.setName("全部商品分类");
		List<TreeNode> listtreenode  = goodsClassDao.queryListTree();
		listtreenode.add(node);
		return listtreenode;
	}

	@Override
	public SzlGoodsClass getById(Integer id) {
		return goodsClassDao.get(SzlGoodsClass.class, id);
	}

	@Override
	public String save(SzlGoodsClass goodsClass) {
		Assert.isTrue(StringUtils.hasLength(goodsClass.getClassname()), "商品分类名称不能为空!");
		Assert.isTrue(StringUtils.hasLength(goodsClass.getClassicon()), "商品分类图标不能为空!");
		if (goodsClass.getIshot()==null) goodsClass.setIshot(2);
		if (goodsClass.getIsrecommend()==null) goodsClass.setIsrecommend(2);
		Assert.isTrue(goodsClass.getClasssort()!=null, "顺序不能为空!");
		if(!goodsClassDao.isUnique(goodsClass, new String[]{"classname"})){
			throw new CapecException("["+goodsClass.getClassname()+"]商品分类名称已经存在,不允许重复添加!");
		}
		super.save(goodsClass);
		return null;
	}

	@Override
	public String update(SzlGoodsClass goodsClass) {
		Assert.isTrue(goodsClass.getId()!=null, "更新时ID不能为空!");
		Assert.isTrue(StringUtils.hasLength(goodsClass.getClassname()), "商品分类名称不能为空!");
		Assert.isTrue(StringUtils.hasLength(goodsClass.getClassicon()), "商品分类图标不能为空!");
		if (goodsClass.getIshot()==null) goodsClass.setIshot(2);
		if (goodsClass.getIsrecommend()==null) goodsClass.setIsrecommend(2);
		Assert.isTrue(goodsClass.getClasssort()!=null, "顺序不能为空!");
		if(!goodsClassDao.isUnique(goodsClass, new String[]{"classname"})){
			throw new CapecException("["+goodsClass.getClassname()+"]商品分类名称已经存在,不允许重复添加!");
		}
		super.update(goodsClass);
		return null;
	}

	@Override
	public String delete(String ids) {
		Integer count = goodsDao.getCountByGoodsClassIds(ids);
		Assert.isTrue(count<=0, "要删除的分类正在使用中,请删除商品后重试.");
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(SzlGoodsClass.class, ids, IdTypes.Integer);
		return "删除成功!";
	
	}
	
	@Override
	public String updateFieidById(String fieid, Integer value, String ids) {
		goodsClassDao.updateFieidById(fieid, value, ids);
		return "更新成功!";
	}
	
	
}
