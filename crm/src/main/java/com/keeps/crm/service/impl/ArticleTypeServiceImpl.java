package com.keeps.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.crm.dao.ArticleDao;
import com.keeps.crm.dao.ArticleTypeDao;
import com.keeps.crm.service.ArticleTypeService;
import com.keeps.utils.Constants;
import com.keeps.utils.TreeNode;
import com.keeps.model.TArticleType;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ArticleTypeServiceImpl.java</p>  
 * <p>Description: 文章分类SERVICE实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ArticleTypeServiceImpl extends AbstractService implements ArticleTypeService {

	@Autowired
	private ArticleTypeDao articleTypeDao;
	@Autowired
	private ArticleDao articleDao;
	@Override
	public Page queryList(TArticleType articleType) {
		Page page = articleTypeDao.queryList(articleType);
		return page;
	}

	public List<TArticleType> queryListAll(){
		return articleTypeDao.queryListAll();
	}

	@Override
	public List<TreeNode> queryListTree() {
		TreeNode node  = new TreeNode();
		node.setId("0");
		node.setpId("0");
		node.setOpen(1);
		node.setName("全部栏目");
		List<TreeNode> listtreenode  = articleTypeDao.queryListTree();
		listtreenode.add(node);
		return listtreenode;
	}

	/**
	 * 根据id获得栏目信息
	 */
	public TArticleType getById(Integer id){
		return super.get(TArticleType.class, id);
	}

	@Override
	public String save(TArticleType articleType) {
		Assert.isTrue(StringUtils.hasLength(articleType.getName()), "栏目名称不能为空!");
		Assert.isTrue(articleType.getPid()!=null, "上级栏目不能为空!");
		Assert.isTrue(articleType.getTemplatetype()!=null, "栏目类型不能为空!");
		if(StringUtils.notText(Constants.TEMPLATE_TYPE.get(articleType.getTemplatetype()))){
			throw new CapecException("栏目类型不正确!");
		}
		if (1==articleType.getTemplatetype()) {//栏目类型是列表页
			Assert.isTrue(StringUtils.hasLength(articleType.getListtemplateurl()), "列表页模版不能为空!");
		}
		Assert.isTrue(StringUtils.hasLength(articleType.getArticletemplateurl()), "内容页模版不能为空!");
		Assert.isTrue(articleType.getSort()!=null, "顺序不能为空!");
		if(!articleTypeDao.isUnique(articleType, new String[]{"name"})){
			throw new CapecException("["+articleType.getName()+"]栏目名称已经存在,不允许重复添加!");
		}
		super.save(articleType);
		return null;
	}

	@Override
	public String update(TArticleType articleType) {
		Assert.isTrue(articleType.getId()!=null, "ID不能为空!");
		Assert.isTrue(StringUtils.hasLength(articleType.getName()), "栏目名称不能为空!");
		Assert.isTrue(articleType.getPid()!=null, "上级栏目不能为空!");
		Assert.isTrue(articleType.getTemplatetype()!=null, "栏目类型不能为空!");
		if(StringUtils.notText(Constants.TEMPLATE_TYPE.get(articleType.getTemplatetype()))){
			throw new CapecException("栏目类型不正确!");
		}
		if (1==articleType.getTemplatetype()) {//栏目类型是列表页
			Assert.isTrue(StringUtils.hasLength(articleType.getListtemplateurl()), "列表页模版不能为空!");
		}
		Assert.isTrue(StringUtils.hasLength(articleType.getArticletemplateurl()), "内容页模版不能为空!");
		Assert.isTrue(articleType.getSort()!=null, "顺序不能为空!");
		if(!articleTypeDao.isUnique(articleType, new String[]{"name"})){
			throw new CapecException("["+articleType.getName()+"]栏目名称已经存在,不允许重复添加!");
		}
		super.update(articleType,EditType.NULL_UN_UPDATE);
		return null;
	}

	@Override
	public String delete(String ids) {
		Integer count = articleDao.getCountByTypeids(ids);
		Assert.isTrue(count<=0, "要删除的栏目正在使用中,请删除文章后重试.");
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(TArticleType.class, ids, IdTypes.Integer);
		return "删除成功!";
	}
	
}
