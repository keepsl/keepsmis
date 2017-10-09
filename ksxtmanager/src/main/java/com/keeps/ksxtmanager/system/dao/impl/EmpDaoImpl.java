package com.keeps.ksxtmanager.system.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.ksxtmanager.system.dao.EmpDao;
import com.keeps.model.TEmp;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: EmpDaoImpl.java</p>  
 * <p>Description: 员工DAO实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class EmpDaoImpl extends AbstractDao implements EmpDao {

	@Override
	public Page queryList(TEmp emp) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT a.*,b.name as orgname,group_concat(d.id) as roleids,group_concat(d.name) as rolenames ");
		sb.append(" FROM t_emp a ");
		sb.append(" left JOIN t_org b ON a.orgid = b.id ");
		sb.append(" left join t_emprole c on a.id = c.empid ");
		sb.append(" left join t_role d on c.roleid = d.id ");
		sb.append(" where  a.isadmin<>1 ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(emp.getName())) {
			sb.append(" and (a.name like ? or a.pym like ? or a.jobnumber like ?) ");
			values.add("%"+emp.getName().trim()+"%");
			values.add("%"+emp.getName().trim()+"%");
			values.add("%"+emp.getName().trim()+"%");
		}
		if (emp.getOrgid()!=null && emp.getOrgid().intValue()!=0) {
			sb.append(" and FIND_IN_SET(a.orgid, getOrgChildList(?)) ");
			values.add(emp.getOrgid());
		}/*
		if (StringUtils.hasText(emp.getJobnumber())) {
			sb.append(" and a.jobnumber like ? ");
			values.add("%"+emp.getJobnumber().trim()+"%");
		}
*/
		if (emp.getStatus() != null) {
			sb.append(" and a.status = ? ");
			values.add(emp.getStatus());
		}
		sb.append(" group by a.id ");
		if(StringUtils.hasText(emp.getSidx()) && StringUtils.hasText(emp.getSord())) {
        	sb.append("order by "+emp.getSidx()+" "+emp.getSord());
		}else{
			sb.append("  order by a.id desc ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), emp, TEmp.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TEmp> getList(TEmp emp) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_emp a where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(emp.getName())) {
			sb.append(" and (name like ? or pym like ? or jobnumber like ?)");
			values.add("%"+emp.getName().trim()+"%");
			values.add("%"+emp.getName().trim()+"%");
			values.add("%"+emp.getName().trim()+"%");
		}
		if (emp.getId()!=null) {
			sb.append(" and id = ? ");
			values.add(emp.getId());
		}
		if (emp.getStatus() != null) {
			sb.append(" and status = ? ");
			values.add(emp.getStatus());
		}
		sb.append("  order by name ");
		return super.getByPropertySql(sb.toString(), values.toArray(), TEmp.class);
	}

	@SuppressWarnings("unchecked")
	public List<TEmp> getFzridsByClientid(Integer clientid){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.id,a.name from t_emp a inner join t_empclient b on a.id = b.empid and b.clientid = ? ");
		sb.append("  order by a.name ");
		List<TEmp> list = super.getByPropertySql(sb.toString(), new Object[]{clientid}, TEmp.class);
		return CollectionUtils.isEmpty(list)?null:list;
	}
	
	@SuppressWarnings("rawtypes")
	public Integer countByPwdEmpid(Integer id,String pwd){
		List list = super.getByPropertySql(" select count(1) from t_emp where id = ? and password=? ", new Object[]{id,pwd});
		return Integer.parseInt(list.get(0).toString());
	}

	
	@SuppressWarnings("unchecked")
	public TEmp getById(Integer id){
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT a.*,b.name as orgname,group_concat(d.id) as roleids,group_concat(d.name) as rolenames ");
		sb.append(" FROM t_emp a ");
		sb.append(" left JOIN t_org b ON a.orgid = b.id ");
		sb.append(" left join t_emprole c on a.id = c.empid ");
		sb.append(" left join t_role d on c.roleid = d.id ");
		sb.append(" where a.id =? and a.isadmin<>1 ");
		List<TEmp> list = super.getByPropertySql(sb.toString(), new Object[]{id}, TEmp.class);
		return (CollectionUtils.isEmpty(list)) ? null : list.get(0);
	}

	@SuppressWarnings("rawtypes")
	public String getMaxJobnumber(){
		List list = super.getByPropertySql("select max(jobnumber) from t_emp where isadmin <> 1 ", null);
		return (CollectionUtils.isEmpty(list)) ? "0":(list.get(0)==null?"0":list.get(0).toString());
	}

	public Integer deleteEmproleByEmpid(Integer empid){
		return super.executeSQL("delete from t_emprole where empid = ? ", new Object[]{empid});
	}

	public Integer deleteEmproleByEmpids(String empids){
		return super.executeSQL("delete from t_emprole where empid in (:empids) ", new String[]{"empids"} ,new Object[]{empids.split(",")});
	}

	public Integer deleteEmpclientByEmpids(String empids){
		return super.executeSQL("delete from t_empclient where empid in (:empids) ", new String[]{"empids"} ,new Object[]{empids.split(",")});
	}

	@SuppressWarnings("rawtypes")
	public Integer countByOrgids(String orgids){
		List list = super.getByNameParamSql(" select count(1) from t_emp where orgid in (:orgids) ", new String[]{"orgids"}, new Object[]{orgids.split(",")});
		return Integer.parseInt(list.get(0).toString());
	}

	

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getClientGroupByEmp(){
		StringBuffer sb = new StringBuffer();
		sb.append(" select b.empid,count(1) as clientnum,max(c.name) as name from t_client a inner join t_empclient b ");
		sb.append(" on a.id = b.clientid ");
		sb.append(" inner join t_emp c on b.empid = c.id ");
		sb.append(" group by b.empid ");
		sb.append("  order by c.name ");
		List<Map<String, Object>> list = super.getByPropertySql(sb.toString(),null,HashMap.class);
		return list;
	}
	
	
	public List<Map<String, Object>> getContactGroupByEmp(String contacttimesta,String contacttimeend){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select a.contactnum,b.name,b.id from (select count(1) as contactnum,a.empid from t_contact_record a where 1=1 ");
		if (StringUtils.hasText(contacttimesta)) {//联系开始日
			sb.append(" and date_format(a.contacttime,'%Y-%m-%d') >= str_to_date(?,'%Y-%m-%d') ");
			values.add(contacttimesta.trim());
		}
		if (StringUtils.hasText(contacttimeend)) {//联系结束日
			sb.append(" and date_format(a.contacttime,'%Y-%m-%d') <= str_to_date(?,'%Y-%m-%d') ");
			values.add(contacttimeend.trim());
		}
		sb.append(" group by a.empid) a ");
		sb.append(" inner join t_emp b on a.empid = b.id ");
		List<Map<String, Object>> list = super.getByPropertySql(sb.toString(),values.toArray(),HashMap.class);
		return list;
	}



}
