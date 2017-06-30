package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.MenuDao;
import com.keeps.utils.TreeNode;
import com.keeps.model.TManagerMenu;
import com.keeps.model.TOperateMethod;
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
				hql.append(" where pid = ? or id = ? ");
				values.add(menu.getPid());
				values.add(menu.getPid());
			}
		}
		return super.queryByNameParamHql(hql.toString() ,null, values.toArray(), menu);
	}
	
	
	public Integer countUrlByRole(Integer userid,String url){
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) from ( ");
		sql.append(" select a.id,d.menuname as name,d.url from t_emprole a inner join t_role b on a.roleid = b.id ");
		sql.append(" inner join t_menu_access c on b.id = c.relid and c.type = 1 ");
		sql.append(" inner join t_manager_menu d on c.menuid = d.id and d.status = 1 ");
		sql.append(" where a.empid = ? ");
		sql.append(" union ");
		sql.append(" select a.id,f.name as mothod,f.code from t_emprole a inner join t_role b on a.roleid = b.id ");
		sql.append(" inner join t_operate_access e on b.id = e.relid and e.type = 1 ");
		sql.append(" inner join t_operate_method f on e.operateid = f.id ");
		sql.append(" where a.empid = ? ) a where a.url = ? ");

		List list = super.getByPropertySql(sql.toString(), new Object[]{userid,userid,url});
		return (CollectionUtils.isEmpty(list)) ? 0:Integer.parseInt(list.get(0).toString());
	}

	@SuppressWarnings("unchecked")
	public TManagerMenu getById(Integer id){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,group_concat(concat( b.name,':',b.code) separator '|') as operates  ");
		sb.append(" from t_manager_menu a left join t_operate_method b on a.id = b.menuid ");
		sb.append(" where a.id = ? ");
		sb.append(" group by a.id ");
		List<TManagerMenu> list = super.getByPropertySql(sb.toString(), new Object[]{id}, TManagerMenu.class);
		return (CollectionUtils.isEmpty(list)) ? null : list.get(0);
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

	
	@SuppressWarnings("unchecked")
	public List<TManagerMenu> queryListByUserRole(Integer empid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select d.id,d.pid,d.menuname,d.url,d.icon from t_emprole a inner join t_role b on a.roleid = b.id ");
		sb.append(" inner join t_menu_access c on b.id = c.relid and c.type = 1 ");
		sb.append(" inner join t_manager_menu d on c.menuid = d.id and d.status = 1 ");
		sb.append(" where a.empid = ? ");
		sb.append(" GROUP BY d.id ");
		sb.append("order by d.sort ");
		List<Object> values = new ArrayList<Object>();
		values.add(empid);
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
	public List<TreeNode> queryListTreeAll(){
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,pid as pId,url,icon,menuname as name,'1' as type from t_manager_menu ");
		List<Object> values = new ArrayList<Object>();
		sb.append(" where status= ? ");
		values.add(1);
		sb.append("order by sort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),TreeNode.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<TreeNode> queryListTreeByUserRole(Integer empid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select d.id,d.pid as pId,d.menuname as name,'1' as type from t_emprole a inner join t_role b on a.roleid = b.id ");
		sb.append(" inner join t_menu_access c on b.id = c.relid and c.type = 1 ");
		sb.append(" inner join t_manager_menu d on c.menuid = d.id and d.status = 1 ");
		sb.append(" where a.empid = ? ");
		sb.append(" GROUP BY d.id ");
		List<Object> values = new ArrayList<Object>();
		values.add(empid);
		sb.append("order by d.sort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),TreeNode.class);
	}

	
	public Integer deleteOperateMethodByCode(Integer menuid,String code){
		return super.executeSQL(" delete from t_operate_method where menuid = ? and code = ? ", new Object[]{menuid,code});
	}
	
	public Integer deleteOperateMethodByMenuid(Integer menuid){
		return super.executeSQL(" delete from t_operate_method where menuid = ? ", new Object[]{menuid});
	}

	@SuppressWarnings("unchecked")
	public List<TOperateMethod> getOperateMethodList(String menuids){
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_operate_method where menuid in (:menuids) ");
		return super.getByNameParamSql(sb.toString(),new String[]{"menuids"},new Object[]{menuids.split(",")},TOperateMethod.class);
	}


	@SuppressWarnings("rawtypes")
	public Integer countGrantMenuByMenuid(Integer roleid,Integer menuid){
		List list = super.getByPropertySql(" select count(1) from t_menu_access where relid = ? and menuid = ? ", new Object[]{roleid,menuid});
		return Integer.parseInt(list.get(0).toString());
	}
	
	@SuppressWarnings("rawtypes")
	public Integer countGrantOperateByOperateid(Integer roleid,Integer operateid){
		List list = super.getByPropertySql(" select count(1) from t_operate_access where relid = ? and operateid = ? ", new Object[]{roleid,operateid});
		return Integer.parseInt(list.get(0).toString());
	}

	public Integer countOperateByCode(String code){
		List list = super.getByPropertySql(" select count(1) from t_operate_method where code = ? ", new Object[]{code});
		return Integer.parseInt(list.get(0).toString());
	}

	public Integer updateOperateByCode(String name,String code){
		return super.executeSQL(" update t_operate_method set name = ? where code = ?",  new Object[]{name,code});
	}

	
	@Override
	public Integer deleteMenuAccess(Integer relid, Integer type) {
		return super.executeSQL("delete from t_menu_access where relid = ? and type = ? ", new Object[]{relid,type});
	}

	@Override
	public Integer deleteOperateAccess(Integer relid, Integer type) {
		return super.executeSQL("delete from t_operate_access where relid = ? and type = ? ", new Object[]{relid,type});
	}

	public Integer deleteMenuOperateAccessByMenuids(String menuids){
		super.executeSQL("DELETE a FROM t_operate_access a ,t_operate_method b WHERE a.operateid=b.id and b.menuid in (:menuids) ", new String[]{"menuids"} ,new Object[]{menuids.split(",")});
		super.executeSQL("delete from t_menu_access where menuid in (:menuids) and type = 1 ", new String[]{"menuids"} ,new Object[]{menuids.split(",")});
		return super.executeSQL("delete from t_operate_method where menuid in (:menuids) ", new String[]{"menuids"} ,new Object[]{menuids.split(",")});
	}


	
}
