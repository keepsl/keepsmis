package com.keeps.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.crm.dao.RoleDao;
import com.keeps.crm.service.RoleService;
import com.keeps.model.TRole;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: RoleServiceImpl.java</p>  
 * <p>Description: 角色Service实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class RoleServiceImpl extends AbstractService implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public Page queryList(TRole role) {
		return roleDao.queryList(role);
	}

	@Override
	public List<TRole> getList(TRole role) {
		return roleDao.getList(role);
	}

	@Override
	public TRole getById(Integer id){
		return super.get(TRole.class, id);
	}
	
	public String saveOrUpdate(TRole role){
		Assert.isTrue(StringUtils.hasText(role.getName()), "角色名称不能为空!");
		Assert.isTrue(role.getStatus()!=null, "角色状态不能为空!");
		Assert.isTrue(role.getRoletype()!=null, "角色类型不能为空!");
		role.setSort(0);
		if(!roleDao.isUnique(role, new String[]{"name"})){
			throw new CapecException("["+role.getName()+"]角色名称已经存在,不允许重复添加!");
		}
		return super.saveOrUpdateEntity(role,EditType.NULL_UN_UPDATE);
	}

	public String delete(String ids){
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(TRole.class, ids, IdTypes.Integer);
		roleDao.deleteMenuOperateAccessByRoleids(ids);
		roleDao.deleteEmproleByRoleids(ids);
		return "删除成功!";
	}
	
	
	public List<TreeNode> getRoleTree(String roleids){
		TreeNode node  = new TreeNode();
		node.setId("0");
		node.setpId("0");
		node.setOpen(1);
		node.setName("全部组织");
		List<TreeNode> listtreenode  = roleDao.getListTree();
		if (StringUtils.hasText(roleids)) {
			String[] roleidsp = roleids.split(",");
			for (TreeNode treeNode : listtreenode) {
				for (String roleid : roleidsp) {
					if(treeNode.getId().equals(roleid)){
						treeNode.setChecked(1);
					}
				}
			}
		}
		listtreenode.add(node);
		return listtreenode;
	}
	
	public String getRolenamesByEmpid(){
		if (UserSchoolThread.get().isSuperAdmin()) {
			return "超级管理员";
		}
		List<TRole> listrole = roleDao.getListByEmpid(UserSchoolThread.get().getUserid());
		if (listrole==null) {
			return "";
		}
		String rolenames = "";
		for (TRole tRole : listrole) {
			rolenames += tRole.getName()+","; 
		}
		return StringUtils.hasText(rolenames)?rolenames.substring(0, rolenames.length()-1):rolenames;
	}


}
