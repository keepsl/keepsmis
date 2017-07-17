package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.ContactRecordDao;
import com.keeps.model.TContactRecord;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ContactRecordDaoImpl.java</p>  
 * <p>Description: 客户联系记录DAO实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class ContactRecordDaoImpl extends AbstractDao implements ContactRecordDao {

	public Page queryList(TContactRecord contactRecord){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,b.name as empname from t_contact_record a left join t_emp b on a.empid = b.id ");
		sb.append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (contactRecord.getClientid()!=null) {
			sb.append(" and a.clientid = ? ");
			values.add(contactRecord.getClientid());
		}
		if (StringUtils.hasText(contactRecord.getEmpname())) {
			sb.append(" and (b.name like ? or b.pym like ? or b.jobnumber like ? ) ");
			values.add("%"+contactRecord.getEmpname().trim()+"%");
			values.add("%"+contactRecord.getEmpname().trim()+"%");
			values.add("%"+contactRecord.getEmpname().trim()+"%");
		}
		if (StringUtils.hasText(contactRecord.getContacttimestr())) {
			sb.append(" and date_format(a.contacttime,'%Y-%m-%d') = ? ");
			values.add(DateUtils.format(contactRecord.getContacttimestr(), "yyyy-MM-dd"));
		}
		if(StringUtils.hasText(contactRecord.getSidx()) && StringUtils.hasText(contactRecord.getSord())) {
			if (contactRecord.getSidx().equals("empname")) {
	        	sb.append("order by b.name "+contactRecord.getSord());
			}else{
	        	sb.append("order by a."+contactRecord.getSidx()+" "+contactRecord.getSord());
			}
		}else{
			sb.append("  order by a.contacttime desc ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), contactRecord, TContactRecord.class);
	}
	
	public Page queryStreamList(TContactRecord contactRecord,boolean isadmin){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" select a.id,b.name as clientname,b.phone as clientphone,a.contacttime,a.visittime,a.createtime,a.remark,c.fzempname AS fzempname,d.name as empname ");
		sb.append("  from t_contact_record a inner join t_client b on a.clientid = b.id  ");
		if (contactRecord.getFzempid()!=null) {
			sb.append("   inner join   ");
		}else{
			sb.append("   left join   ");
		}
		sb.append("  (select group_concat(b.name) fzempname,group_concat(b.id) fzempid,a.clientid from t_empclient a ");
		sb.append("  inner join  t_emp b on a.empid = b.id  ");
		if (!isadmin) {
			sb.append(" and a.empid = ? ");
			values.add(contactRecord.getFzempid());
		}else{
			if (contactRecord.getFzempid()!=null) {
				sb.append(" and a.empid = ? ");
				values.add(contactRecord.getFzempid());
			}
		}
		sb.append("    group by a.clientid) ");
		sb.append("  c ON b.id = c.clientid ");
		sb.append(" left join t_emp d on a.empid = d.id ");
		sb.append(" where 1 = 1 ");
		
		if (StringUtils.hasText(contactRecord.getClientname())) {
			sb.append(" and (b.name like ? or b.pym like ? ) ");
			values.add("%"+contactRecord.getClientname().trim()+"%");
			values.add("%"+contactRecord.getClientname().trim()+"%");
		}
		if (StringUtils.hasText(contactRecord.getClientphone())) {
			if (contactRecord.getClientphone().length()==4) {
				sb.append(" and right(b.phone,4) = ? ");
				values.add(contactRecord.getClientphone().trim());	
			}else{
				sb.append(" and b.phone like ? ");
				values.add("%"+contactRecord.getClientphone().trim()+"%");
			}
		}
		if (StringUtils.hasText(contactRecord.getContacttimesta())) {
			sb.append(" and date_format(a.contacttime,'%Y-%m-%d') >= str_to_date(?,'%Y-%m-%d') ");
			values.add(DateUtils.format(contactRecord.getContacttimesta(), "yyyy-MM-dd"));
		}
		if (StringUtils.hasText(contactRecord.getContacttimeend())) {
			sb.append(" and date_format(a.contacttime,'%Y-%m-%d') <= str_to_date(?,'%Y-%m-%d') ");
			values.add(DateUtils.format(contactRecord.getContacttimeend(), "yyyy-MM-dd"));
		}
		if(StringUtils.hasText(contactRecord.getSidx()) && StringUtils.hasText(contactRecord.getSord())) {
			if (contactRecord.getSidx().equals("clientname")) {
	        	sb.append("order by b.name "+contactRecord.getSord());
			}else if (contactRecord.getSidx().equals("clientphone")) {
	        	sb.append("order by b.phone "+contactRecord.getSord());
			}else if (contactRecord.getSidx().equals("fzempname")) {
	        	sb.append("order by c.fzempname "+contactRecord.getSord());
			}else if(contactRecord.getSidx().equals("empname")){
	        	sb.append("order by d.name "+contactRecord.getSord());
			}else{
	        	sb.append("order by a."+contactRecord.getSidx()+" "+contactRecord.getSord());
			}
		}else{
			sb.append("  order by a.contacttime desc ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), contactRecord, TContactRecord.class);
	}

	
	public Page queryStatisticsList(TContactRecord contactRecord,boolean isadmin){
		StringBuffer sb = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		sb.append(" SELECT b.id, b.NAME AS clientname, b.phone AS clientphone,a.contactnum,a.contacttime,a.visittime,c.fzempname AS fzempname ");
		sb.append(" FROM (select a.clientid,count(1) contactnum,max(a.contacttime) as contacttime,max(a.visittime) as visittime from t_contact_record a group by a.clientid) a ");
		sb.append(" INNER JOIN t_client b ON a.clientid = b.id ");
		if (contactRecord.getEmpid()!=null) {
			sb.append("   inner join   ");
		}else{
			sb.append("   left join   ");
		}
		sb.append("  (select group_concat(b.name) fzempname,group_concat(b.id) fzempid,a.clientid from t_empclient a ");
		sb.append("  inner join  t_emp b on a.empid = b.id  ");
		if (!isadmin) {
			sb.append(" and a.empid = ? ");
			values.add(contactRecord.getEmpid());
		}else{
			if (contactRecord.getEmpid()!=null) {
				sb.append(" and a.empid = ? ");
				values.add(contactRecord.getEmpid());
			}
		}
		sb.append("    group by a.clientid) ");
		sb.append("  c ON b.id = c.clientid ");

		sb.append(" where 1 = 1 ");
		
		if (StringUtils.hasText(contactRecord.getClientname())) {
			sb.append(" and (b.name like ? or b.pym like ? ) ");
			values.add("%"+contactRecord.getClientname().trim()+"%");
			values.add("%"+contactRecord.getClientname().trim()+"%");
		}
		if (StringUtils.hasText(contactRecord.getClientphone())) {
			if (contactRecord.getClientphone().length()==4) {
				sb.append(" and right(b.phone,4) = ? ");
				values.add(contactRecord.getClientphone().trim());	
			}else{
				sb.append(" and b.phone like ? ");
				values.add("%"+contactRecord.getClientphone().trim()+"%");
			}
		}
		if (StringUtils.hasText(contactRecord.getContacttimesta())) {
			sb.append(" and date_format(a.contacttime,'%Y-%m-%d') >= str_to_date(?,'%Y-%m-%d') ");
			values.add(DateUtils.format(contactRecord.getContacttimesta(), "yyyy-MM-dd"));
		}
		if (StringUtils.hasText(contactRecord.getContacttimeend())) {
			sb.append(" and date_format(a.contacttime,'%Y-%m-%d') <= str_to_date(?,'%Y-%m-%d') ");
			values.add(DateUtils.format(contactRecord.getContacttimeend(), "yyyy-MM-dd"));
		}
		if(StringUtils.hasText(contactRecord.getSidx()) && StringUtils.hasText(contactRecord.getSord())) {
			if (contactRecord.getSidx().equals("clientname")) {
	        	sb.append("order by b.name "+contactRecord.getSord());
			}else if (contactRecord.getSidx().equals("clientphone")) {
	        	sb.append("order by b.phone "+contactRecord.getSord());
			}else if (contactRecord.getSidx().equals("fzempname")) {
	        	sb.append("order by c.fzempname "+contactRecord.getSord());
			}else{
	        	sb.append("order by a."+contactRecord.getSidx()+" "+contactRecord.getSord());
			}
		}else{
			sb.append("  order by a.contactnum DESC ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), contactRecord, TContactRecord.class);
	}


}
