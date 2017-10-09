package com.keeps.ksxtmanager.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.ksxtmanager.system.dao.EmpDao;
import com.keeps.ksxtmanager.system.dao.OrgDao;
import com.keeps.ksxtmanager.system.service.OrgService;
import com.keeps.model.TOrg;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: OrgServiceImpl.java</p>  
 * <p>Description: 组织Service实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class OrgServiceImpl extends AbstractService implements OrgService {
	
	@Autowired
	private OrgDao orgDao;
	@Autowired
	private EmpDao empDao;

	@Override
	public Page queryList(TOrg org) {
		return orgDao.queryList(org);
	}

	@Override
	public List<TOrg> getList(TOrg org) {
		return orgDao.getList(org);
	}

	@Override
	public TOrg getById(Integer id) {
		return super.get(TOrg.class, id);
	}
	
	public List<TreeNode> getOrgTree(String orgids){
		TreeNode node  = new TreeNode();
		node.setId("0");
		node.setpId("0");
		node.setOpen(1);
		node.setName("全部组织");
		List<TreeNode> listtreenode  = orgDao.getListTree();
		if (StringUtils.hasText(orgids)) {
			String[] orgidsp = orgids.split(",");
			for (TreeNode treeNode : listtreenode) {
				for (String orgid : orgidsp) {
					if(treeNode.getId().equals(orgid)){
						treeNode.setChecked(1);
					}
				}
			}
		}
		listtreenode.add(node);
		return listtreenode;
	}

	@Override
	public String saveOrUpdate(TOrg org) {
		Assert.isTrue(StringUtils.hasText(org.getName()), "组织名称不能为空!");
		Assert.isTrue(org.getPid()!=null, "上级组织不能为空!");
		if (org.getPid()==0) {
			org.setPname("一级组织");
		}else{
			TOrg menu2 = super.get(TOrg.class, org.getPid());
			org.setPname(menu2.getName());
		}
		if(!orgDao.isUnique(org, new String[]{"name"})){
			throw new CapecException("["+org.getName()+"]组织名称已经存在,不允许重复添加!");
		}
		if (org.getId()!=null) {
			TOrg sOrg = new TOrg();
			sOrg.setPid(org.getId());
			List<TOrg> list = orgDao.getList(sOrg);
			for (TOrg mo : list) {
				mo.setPname(org.getName());
			}
			super.saveOrUpdateAllEntity(list, EditType.NULL_UN_UPDATE);
		}

		org.setOrgtype(0);
		org.setSort(0);
		super.saveOrUpdateEntity(org, EditType.NULL_UN_UPDATE);
		return null;
	}

	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		Integer count = orgDao.countByIds(ids);
		Assert.isTrue(count<=0, "所选择的组织下有子组织存在,不允许删除.");
		count = empDao.countByOrgids(ids);
		Assert.isTrue(count<=0, "所选择的组织下有人员存在,不允许删除.");
		super.removeEntity(TOrg.class, ids, IdTypes.Integer);
		return "删除成功!";
	
	}
	
}
