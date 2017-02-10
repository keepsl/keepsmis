package com.keeps.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.manage.dao.MenuDao;
import com.keeps.manage.utils.TreeNode;
import com.keeps.model.TManagerMenu;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/**
 * 
 * <p>Title: MenuDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class MenuDaoImpl extends AbstractDao implements MenuDao {

	/**
	 * 查询菜单列表
	 */
	@Override
	public Page queryList(TManagerMenu menu) {
		StringBuffer hql = new StringBuffer("from TManagerMenu ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasLength(menu.getMenuname())) {
			hql.append(" where menuname like ? ");
			values.add("%"+menu.getMenuname().trim()+"%");
		}
		if (menu.getPid()!=null) {
			if (menu.getPid()!=0) {
				hql.append(" where pid = ? or id = ?");
				values.add(menu.getPid());
				values.add(menu.getPid());
			}
		}
		return super.queryByNameParamHql(hql.toString() ,null, values.toArray(), menu);
	}
	
	/**
	 * 查询所有节点
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<TManagerMenu> queryListAll(Integer status,Integer pid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,pid,url,icon,menuname from t_manager_menu where 1=1 ");
		//Object[] params = null ;
		List<Object> values = new ArrayList<Object>();
		if (status!=null) {
			sb.append(" and status= ? ");
			values.add(status);
		}
		if (pid!=null) {
			sb.append(" and pid= ? ");
			values.add(pid);
		}
		sb.append("order by sort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),TManagerMenu.class);
	}

	/**
	  * @Title:			getListByPid 
	  * @Description:	根据PID取得菜单列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月23日
	 */
	@SuppressWarnings("unchecked")
	public List<TManagerMenu> getListByPid(Integer pid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,pid,menuname,pname from t_manager_menu ");
		List<Object> values = new ArrayList<Object>();
		sb.append(" where pid= ? ");
		values.add(pid);
		return super.getByPropertySql(sb.toString(),values.toArray(),TManagerMenu.class);
	}

	/**
	 * 查询菜单树
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryListTree(){
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,pid as pId,url,icon,menuname as name from t_manager_menu ");
		List<Object> values = new ArrayList<Object>();
		sb.append(" where status= ? ");
		values.add(1);
		sb.append("order by sort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),TreeNode.class);
	}

}
