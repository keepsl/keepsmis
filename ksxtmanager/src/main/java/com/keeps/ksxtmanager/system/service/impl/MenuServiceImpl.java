package com.keeps.ksxtmanager.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.ksxtmanager.system.dao.MenuDao;
import com.keeps.ksxtmanager.system.service.MenuService;
import com.keeps.utils.TreeNode;
import com.keeps.model.TManagerMenu;
import com.keeps.model.TMenuAccess;
import com.keeps.model.TOperateAccess;
import com.keeps.model.TOperateMethod;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.AsyncTreeNode;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;

/** 
 * <p>Title: MenuServiceImpl.java</p>  
 * <p>Description: 菜单SERVICE实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class MenuServiceImpl extends AbstractService implements MenuService{
	
	@Autowired
	private MenuDao menuDao;
	
	/**
	 * 菜单编辑
	 */
	@Override
	public String saveOrUpdate(TManagerMenu menu) {
		Assert.isTrue(StringUtils.hasLength(menu.getMenuname()), "菜单名称不能为空!");
		Assert.isTrue(menu.getPid()!=null, "上级菜单不能为空!");
		Assert.isTrue(StringUtils.hasLength(menu.getUrl()), "菜单连接不能为空!");
		Assert.isTrue(menu.getSort()!=null, "顺序不能为空!");
		Assert.isTrue(menu.getStatus()!=null, "状态不能为空!");
		if(!menuDao.isUnique(menu, new String[]{"menuname","pid"})){
			throw new CapecException("["+menu.getMenuname()+"]菜单名称已经存在,不允许重复添加!");
		}
		if (menu.getPid()==0) {
			menu.setPname("一级菜单");
		}else{
			TManagerMenu menu2 = super.get(TManagerMenu.class, menu.getPid());
			menu.setPname(menu2.getMenuname());
		}
		if (menu.getId()!=null) {
			List<TManagerMenu> list = menuDao.getListByPid(menu.getId());
			for (TManagerMenu tManagerMenu : list) {
				tManagerMenu.setPname(menu.getMenuname());
			}
			super.saveOrUpdateAllEntity(list, EditType.NULL_UN_UPDATE);
			
			//menuDao.deleteOperateMethodByMenuid(menu.getId());
		}
		String operates = menu.getOperates();
		if (StringUtils.hasText(operates)) {
			String[] oms = operates.split("\\|");
			for(String om:oms){
				String[] o = om.replaceAll("：", ":").split(":");
				String name = "";
				String code = "";
				if (o.length>=2) {
					name = o[0];
					code = o[1];
					if(code.indexOf("-del")!=-1){
						menuDao.deleteOperateMethodByCode(menu.getId(),code.substring(0, code.indexOf("-del")));
						continue;
					}
					TOperateMethod operateMethod = new TOperateMethod();
					operateMethod.setMenuid(menu.getId());
					operateMethod.setName(name);
					operateMethod.setCode(code);
					
					//判断操作是否存在
					Integer count = menuDao.countOperateByCode(code);
					if (count>0) {
						menuDao.updateOperateByCode(name, code);
						continue;
					}else{
						//menuDao.deleteOperateMethodByCode(menu.getId(),code);
					}
					super.save(operateMethod);
				}
			}
		}
		super.saveOrUpdateEntity(menu, EditType.NULL_UN_UPDATE);
		return null;
	}
	
	/**
	 * 根据ID获得一条菜单信息
	 */
	public TManagerMenu getById(Integer id){
		return menuDao.getById(id);
	}
	
	/**
	 * 查询菜单列表
	 */
	public List<TManagerMenu> queryListAll(Integer status,Integer pid){
		return menuDao.queryListAll(status,pid);
	}

	/**
	 * 查询菜单列表
	 */
	@Override
	public Page queryList(TManagerMenu menu) {
		Page page = menuDao.queryList(menu);
		return page;
	}

	public Integer countUrlByRole(String url){
		if (UserSchoolThread.get().isSuperAdmin()) {
			return 1;
		}
		return menuDao.countUrlByRole(UserSchoolThread.get().getUserid(), url);
	}

	/**
	 * 删除菜单
	 */
	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(TManagerMenu.class, ids, IdTypes.Integer);
		menuDao.deleteMenuOperateAccessByMenuids(ids);
		return "删除成功!";
	}
	/**
	 * 首页获得菜单树
	 */
	public AsyncTreeNode getIndexMenuTree(){
		List<TManagerMenu> listmenu = null;
		if (UserSchoolThread.get().isSuperAdmin()) {
			listmenu = menuDao.queryListAll(1, null);
		}else{
			listmenu = menuDao.queryListByUserRole(UserSchoolThread.get().getUserid());
		}
		AsyncTreeNode tree = new AsyncTreeNode();
		loadMenuChild(listmenu,tree,0,true);
		return tree;
	}

	/**
	 * 菜单模块管理菜单树-级联
	 */
	public List<TreeNode> getMenuTree(){
		TreeNode node  = new TreeNode();
		node.setId("0");
		node.setpId("0");
		node.setOpen(1);
		node.setName("全部菜单");
		List<TreeNode> listtreenode  = menuDao.queryListTreeAll();
		listtreenode.add(node);
		return listtreenode;
	}
	
	
	private static void loadMenuChild(List<TManagerMenu> listmenu,AsyncTreeNode menutree,Integer pid,boolean ishref){
		List<TManagerMenu> childList = getMenuListPid(listmenu,pid);
		if(childList.size()==0) {
			
		}else{
			List<AsyncTreeNode> listtree = new ArrayList<>();
			for(TManagerMenu menu:childList){
				AsyncTreeNode tree = new AsyncTreeNode();
				tree.setId(menu.getId());
				tree.setName(menu.getMenuname());
				tree.setHref(ishref?menu.getUrl():"");
				tree.setIcon(menu.getIcon());
				loadMenuChild(listmenu,tree,menu.getId(),ishref);
				listtree.add(tree);
			}
			menutree.setChildren(listtree);
		}
	}

	private static List<TManagerMenu> getMenuListPid(List<TManagerMenu> list,Integer pid){
		List<TManagerMenu> newmenu = new ArrayList<TManagerMenu>();
		for (TManagerMenu tManagerMenu : list) {
			if(tManagerMenu.getPid().equals(pid))
				newmenu.add(tManagerMenu);
		}
		return newmenu;
	}
	
	public List<TreeNode> getMenuGrantTree(Integer roleid){
		TreeNode node  = new TreeNode();
		node.setId("0");
		node.setpId("0");
		node.setOpen(1);
		node.setName("全部");
		List<TreeNode> listtreenode = null;
		if (UserSchoolThread.get().isSuperAdmin()) {
			listtreenode = menuDao.queryListTreeAll();
		}else{
			listtreenode = menuDao.queryListTreeByUserRole(UserSchoolThread.get().getUserid());
		}
		listtreenode.add(node);
		//判断菜单是否授权
		for (TreeNode treeNode : listtreenode) {
			Integer count = menuDao.countGrantMenuByMenuid(roleid,Integer.parseInt(treeNode.getId()));
			if (count>0) {
				treeNode.setChecked(1);
			}
		}
		
		String menuids = "";
		for (TreeNode treeNode : listtreenode) {
			menuids += treeNode.getId()+",";
		}
		if (StringUtils.hasText(menuids)) {
			menuids = menuids.substring(0, menuids.length()-1);
		}
		
		/***操作方法***/
		List<TOperateMethod> listMethod = menuDao.getOperateMethodList(menuids);
		for (TOperateMethod tOperateMethod : listMethod) {
			node  = new TreeNode();
			node.setId("CZ"+tOperateMethod.getId());
			node.setName(tOperateMethod.getName()+"|"+tOperateMethod.getCode());
			node.setpId(tOperateMethod.getMenuid()+"");
			node.setType(2);
			//判断操作方法是否授权
			Integer count = menuDao.countGrantOperateByOperateid(roleid,tOperateMethod.getId());
			if (count>0) {
				node.setChecked(1);
			}
			listtreenode.add(node);
		}
		return listtreenode;
	}
	
	
	public String saveOrgMenuGrant(Integer roleid,String idarr,String typearr){
		Assert.isTrue(roleid!=null, "请选择要授权的角色.");
		if (StringUtils.hasText(idarr)) {
			Assert.isTrue(StringUtils.hasText(typearr), "不能区分授权类型.");
		}
		menuDao.deleteMenuAccess(roleid, 1);
		menuDao.deleteOperateAccess(roleid, 1);
		if (StringUtils.notText(idarr)) {
			return null;
		}
		String[] ids = idarr.split(",");
		String[] types = typearr.split(",");
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.notText(ids[0])) {
				continue;
			}
			if (types[i].equals("1")) {//菜单
				Integer id = Integer.parseInt(ids[i]);
				TMenuAccess menuAccess = new TMenuAccess();
				menuAccess.setMenuid(id);
				menuAccess.setRelid(roleid);
				menuAccess.setType(1);
				super.save(menuAccess);
			}else if(types[i].equals("2") && ids[i].indexOf("CZ")!=-1){//操作
				Integer id = Integer.parseInt(ids[i].substring(2, ids[i].length()));
				TOperateAccess operateAccess = new TOperateAccess();
				operateAccess.setOperateid(id);
				operateAccess.setRelid(roleid);
				operateAccess.setType(1);
				super.save(operateAccess);
			}
		}
		return null;
	}


}
