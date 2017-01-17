package com.keeps.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.MenuDao;
import com.keeps.manage.service.MenuService;
import com.keeps.manage.utils.LayuiTree;
import com.keeps.model.TManagerMenu;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: MenuServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
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
	 * 菜单保存
	 */
	@Override
	public String save(TManagerMenu menu) {
		Assert.isTrue(StringUtils.hasLength(menu.getMenuname()), "菜单名称不能为空！");
		Assert.isTrue(menu.getPid()!=null, "上级菜单不能为空！");
		Assert.isTrue(StringUtils.hasLength(menu.getUrl()), "菜单连接不能为空！");
		Assert.isTrue(menu.getStatus()!=null, "状态不能为空！");
		if (menu.getPid()==0) {
			menu.setPname("顶级菜单");
		}else{
			TManagerMenu menu2 = super.get(TManagerMenu.class, menu.getPid());
			menu.setPname(menu2.getMenuname());
		}
		super.save(menu);
		return null;
	}

	/**
	 * 菜单编辑
	 */
	@Override
	public String update(TManagerMenu menu) {
		Assert.isTrue(menu.getId()!=null, "ID不能为空！");
		Assert.isTrue(StringUtils.hasLength(menu.getMenuname()), "菜单名称不能为空！");
		Assert.isTrue(menu.getPid()!=null, "上级菜单不能为空！");
		Assert.isTrue(StringUtils.hasLength(menu.getUrl()), "菜单连接不能为空！");
		Assert.isTrue(menu.getStatus()!=null, "状态不能为空！");
		//TManagerMenu menu2 = super.get(TManagerMenu.class, menu.getPid());
		if (menu.getPid()==0) {
			menu.setPname("顶级菜单");
		}else{
			TManagerMenu menu2 = super.get(TManagerMenu.class, menu.getPid());
			menu.setPname(menu2.getMenuname());
		}
		super.update(menu);
		return null;
	}
	
	/**
	 * 根据ID获得一条菜单信息
	 */
	public TManagerMenu getById(Integer id){
		return super.get(TManagerMenu.class, id);
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

	/**
	 * 删除菜单
	 */
	@Override
	public String delete(String ids) {
		super.removeEntity(TManagerMenu.class, ids, IdTypes.Integer);
		return "删除成功!";
	}
	/**
	 * 首页获得菜单树
	 */
	public LayuiTree getIndexMenuTree(){
		List<TManagerMenu> listmenu = menuDao.queryListAll(null,null);
		LayuiTree tree = new LayuiTree();
		loadMenuChild(listmenu,tree,0,true);
		return tree;
	}

	/**
	 * 菜单模块管理菜单树-级联
	 */
	public List<LayuiTree> getMenuTree(){
		List<TManagerMenu> listmenu = menuDao.queryListAll(null,null);
		LayuiTree tree = new LayuiTree();
		tree.setId(0);
		tree.setName("我的菜单");
		tree.setSpread(true);
		loadMenuChild(listmenu,tree,0,false);
		List<LayuiTree> listtree = new ArrayList<>();
		listtree.add(tree);
		return listtree;
	}
	
	
	private static void loadMenuChild(List<TManagerMenu> listmenu,LayuiTree menutree,Integer pid,boolean ishref){
		List<TManagerMenu> childList = getMenuListPid(listmenu,pid);
		if(childList.size()==0) {
			
		}else{
			List<LayuiTree> listtree = new ArrayList<>();
			for(TManagerMenu menu:childList){
				LayuiTree tree = new LayuiTree();
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
}
