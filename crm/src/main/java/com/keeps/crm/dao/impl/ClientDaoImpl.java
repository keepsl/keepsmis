package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.ClientDao;
import com.keeps.model.TClient;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.Constants;

/** 
 * <p>Title: ClientDaoImpl.java</p>  
 * <p>Description: 客户DAO实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class ClientDaoImpl extends AbstractDao implements ClientDao {

	@Override
	public Page queryList(TClient client, Integer operType) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,d.name as attentionname,d.value as attentionvalue,e.name as typename,"
				+ "c.name as linkempname,b.remark as content,b.nexttime,b.updatetime contacttime,"
				+ "f.fzempname,f.fzempid,ca.name as receivename,b.recordnum,g.name as receivetypename from t_client a  ");
		sb.append(" left join ");
		sb.append(" (select a.id,count(1) as recordnum,max(a.clientid) clientid ,max(a.empid) empid,max(a.nexttime) nexttime,max(remark) remark,max(updatetime) updatetime ");
		sb.append(" from (select * from t_contact_record order by id desc) a group by a.clientid) b on a.id = b.clientid ");
		sb.append(" left join t_emp c on b.empid = c.id ");
		sb.append(" left join t_emp ca on a.receiveid = ca.id ");
		sb.append(" left join t_dict d on d.id = a.attention and d.typecode = ? ");
		values.add(Constants.DICT_CODE[2]);
		sb.append(" left join t_dict_type e on e.id = a.type ");
		if (operType.intValue() == 3) {
			sb.append(" inner join ");
		}else{
			if (StringUtils.hasText(client.getFzempid())) {
				sb.append(" inner join ");
			}else{
				sb.append(" left join ");
			}
		}
		sb.append(" (select group_concat(b.name) fzempname,group_concat(b.id) fzempid,a.clientid from t_empclient a left join t_emp b on a.empid = b.id ");
		if (operType.intValue() == 3) {//查询我的客户
			sb.append(" where a.empid = ? ");
			values.add(client.getCreateperson());
		}else{
			if (StringUtils.hasText(client.getFzempid())) {
				sb.append(" where a.empid = ? ");
				values.add(Integer.parseInt(client.getFzempid()));
			}
		}
		
		sb.append(" group by a.clientid) f  on a.id = f.clientid ");
		sb.append(" left join t_dict_type g on g.id = a.receivetype ");
		sb.append(" where a.status = 1 ");
		
		if(operType.intValue() == 1){
			
		}else if(operType.intValue() == 2){
			sb.append(" and a.isopen = 2 ");
		}
		if (StringUtils.hasText(client.getName())) {
			sb.append(" and (a.name like ? or a.pym like ? ) ");
			values.add("%"+client.getName().trim()+"%");
			values.add("%"+client.getName().trim()+"%");
		}
		if (StringUtils.hasText(client.getPhone())) {
			if (client.getPhone().length()==4) {
				sb.append(" and right(a.phone,4) = ? ");
				values.add(client.getPhone().trim());	
			}else{
				sb.append(" and a.phone like ? ");
				values.add("%"+client.getPhone().trim()+"%");
			}
		}
		if (client.getAttention()!=null) {
			sb.append(" and a.attention = ? ");
			values.add(client.getAttention());
		}
		if (client.getType()!=null) {
			sb.append(" and a.type = ? ");
			values.add(client.getType());
		}
		if (client.getReceivetype()!=null) {
			sb.append(" and a.receivetype = ? ");
			values.add(client.getReceivetype());
		}
		if (client.getReceiveid()!=null) {
			sb.append(" and a.receiveid = ? ");
			values.add(client.getReceiveid());
		}
		if (StringUtils.hasText(client.getCreatetimesta())) {//创建开始日
			sb.append(" and date_format(a.createtime,'%Y-%m-%d') >= str_to_date(?,'%Y-%m-%d') ");
			values.add(client.getCreatetimesta().trim());
		}
		if (StringUtils.hasText(client.getCreatetimeend())) {//创建结束日
			sb.append(" and date_format(a.createtime,'%Y-%m-%d') <= str_to_date(?,'%Y-%m-%d') ");
			values.add(client.getCreatetimeend().trim());
		}
		if (StringUtils.hasText(client.getContacttimesta())) {//最近联系时间开始日
			sb.append(" and date_format(b.updatetime,'%Y-%m-%d') >= str_to_date(?,'%Y-%m-%d') ");
			values.add(client.getContacttimesta().trim());
		}
		if (StringUtils.hasText(client.getContacttimeend())) {//最近联系时间结束日
			sb.append(" and date_format(b.updatetime,'%Y-%m-%d') <= str_to_date(?,'%Y-%m-%d') ");
			values.add(client.getContacttimeend().trim());
		}
		if (StringUtils.hasText(client.getNextcontacttimesta())) {//最近联系时间开始日
			sb.append(" and date_format(b.nexttime,'%Y-%m-%d') >= str_to_date(?,'%Y-%m-%d') ");
			values.add(client.getNextcontacttimesta().trim());
		}
		if (StringUtils.hasText(client.getNextcontacttimeend())) {//最近联系时间结束日
			sb.append(" and date_format(b.nexttime,'%Y-%m-%d') <= str_to_date(?,'%Y-%m-%d') ");
			values.add(client.getNextcontacttimeend().trim());
		}
		if(StringUtils.hasText(client.getSidx()) && StringUtils.hasText(client.getSord())) {
			if ("linkempname".equals(client.getSidx())) {
				sb.append("order by c.name "+client.getSord());
			}else if ("attentionname".equals(client.getSidx())){
				sb.append("order by d.sort "+client.getSord());
			}else if ("typename".equals(client.getSidx())){
				sb.append("order by e.name "+client.getSord());
			}else if ("contacttime".equals(client.getSidx())){
				sb.append("order by b.updatetime "+client.getSord());
			}else if ("nexttime".equals(client.getSidx())) {
				sb.append("order by b.nexttime "+client.getSord());
			}else if ("fzempname".equals(client.getSidx())) {
				sb.append("order by f.fzempname "+client.getSord());
			}else if ("recordnum".equals(client.getSidx())) {
				sb.append("order by b.recordnum "+client.getSord());
			}else if ("receivetypename".equals(client.getSidx())) {
				sb.append("order by g.name "+client.getSord());
			}else if ("receivename".equals(client.getSidx())) {
				sb.append("order by ca.name "+client.getSord());
			}else{
				sb.append("order by a."+client.getSidx()+" "+client.getSord());
			}
		}else{
			sb.append("  order by b.updatetime desc ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), client, TClient.class);
	}

	@SuppressWarnings("unchecked")
	public TClient getListById(Integer id){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,d.name as attentionname,d.value as attentionvalue,e.name as typename,"
				+ "c.name as linkempname,b.remark as content,b.nexttime,b.updatetime contacttime,"
				+ "f.fzempname,f.fzempid,ca.name as receivename,g.name as receivetypename from t_client a  ");
		sb.append(" left join (select a.id,max(a.clientid) clientid ,max(a.empid) empid,max(a.nexttime) nexttime,max(remark) remark,max(updatetime) updatetime ");
		sb.append(" from (select * from t_contact_record order by id desc) a group by a.clientid) b on a.id = b.clientid ");
		sb.append(" left join t_emp c on b.empid = c.id ");
		sb.append(" left join t_emp ca on a.receiveid = ca.id ");
		sb.append(" left join t_dict d on d.id = a.attention and d.typecode = ? ");
		values.add(Constants.DICT_CODE[2]);
		sb.append(" left join t_dict_type e on e.id = a.type ");
		sb.append(" left join t_dict_type g on g.id = a.receivetype ");
		sb.append(" left join ");
		sb.append(" (select group_concat(b.name) fzempname,group_concat(b.id) fzempid,a.clientid from t_empclient a left join t_emp b on a.empid = b.id ");
		sb.append(" group by a.clientid) f  on a.id = f.clientid ");
		sb.append(" where a.status = 1 and a.id = ? ");
		values.add(id);
		List<TClient> list = super.getByPropertySql(sb.toString(), values.toArray(), TClient.class);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<TClient> getListByIds(String ids){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,d.name as attentionname,d.value as attentionvalue,e.name as typename,"
				+ "c.name as linkempname,b.remark as content,b.nexttime,b.updatetime contacttime,"
				+ "f.fzempname,f.fzempid,ca.name as receivename,g.name as receivetypename from t_client a  ");
		sb.append(" left join (select a.id,max(a.clientid) clientid ,max(a.empid) empid,max(a.nexttime) nexttime,max(remark) remark,max(updatetime) updatetime ");
		sb.append(" from (select * from t_contact_record order by id desc) a group by a.clientid) b on a.id = b.clientid ");
		sb.append(" left join t_emp c on b.empid = c.id ");
		sb.append(" left join t_emp ca on a.receiveid = ca.id ");
		sb.append(" left join t_dict d on d.id = a.attention and d.typecode = :typecode ");
		values.add(Constants.DICT_CODE[2]);
		sb.append(" left join t_dict_type e on e.id = a.type ");
		sb.append(" left join t_dict_type g on g.id = a.receivetype ");
		sb.append(" left join ");
		sb.append(" (select group_concat(b.name) fzempname,group_concat(b.id) fzempid,a.clientid from t_empclient a left join t_emp b on a.empid = b.id ");
		sb.append(" group by a.clientid) f  on a.id = f.clientid ");
		sb.append(" where a.status = 1 and a.id in (:ids) ");
		values.add(ids.split(","));
		List<TClient> list = super.getByNameParamSql(sb.toString(), new String[]{"typecode","ids"} , values.toArray(), TClient.class);
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public TClient getClientByPhone(String phone,Integer id){
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.id,a.name,fzempid,b.fzempname ");
		sb.append(" from t_client a left join ");
		sb.append(" (select group_concat(b.name) fzempname,group_concat(b.id) fzempid,a.clientid from t_empclient a left join t_emp b on a.empid = b.id ");
		sb.append(" group by a.clientid) b on a.id = b.clientid ");
		sb.append(" where phone = ? ");
		values.add(phone);
		if (id!=null) {
			sb.append(" and id <> ? ");
			values.add(id);
		}
		List<TClient> list = super.getByPropertySql(sb.toString(), values.toArray(), TClient.class);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	public Integer deleteEmpclientByClientids(String clientids){
		return super.executeSQL("delete from t_empclient where clientid in (:clientids) ", new String[]{"clientids"} ,new Object[]{clientids.split(",")});
	}

	@Override
	public Integer updateFieidById(String fieid, String value, String ids){
		String sql = "update t_client set "+fieid+"= :fieid where id in (:ids)";
		return super.executeSQL(sql, new String[]{"fieid","ids"}, new Object[]{value,ids.split(",")});
	}
	

}
