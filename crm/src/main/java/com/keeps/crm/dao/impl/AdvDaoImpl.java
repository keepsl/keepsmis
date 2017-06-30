package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.AdvDao;
import com.keeps.model.TAdv;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class AdvDaoImpl extends AbstractDao implements AdvDao {


	@Override
	public Page queryList(TAdv adv) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(" select a.id,  ");
		sb.append(" a.ap_id as apId, ");
		sb.append(" a.adv_title as advTitle , ");
		sb.append(" a.adv_link as advLink, ");
		sb.append(" a.adv_content as advContent, ");
		sb.append(" a.is_show as isShow, ");
		sb.append(" a.starttime, ");
		sb.append(" a.endtime, ");
		sb.append(" a.slide_sort as slideSort, ");
		sb.append(" a.click_num as clickNum, ");
		sb.append(" a.adv_price as advPrice, ");
		sb.append(" a.createtime as createtime, ");
		sb.append(" b.ap_name as apName, ");
		sb.append(" b.ap_class as apClass, ");
		sb.append(" b.ap_display as apDisplay ");
		sb.append(" from t_adv a , t_adv_position b where a.ap_id = b.id ");
		if (StringUtils.hasText(adv.getAdvTitle())) {
			sb.append(" and a.adv_title = ? ");
			values.add(adv.getAdvTitle().trim());
		}
		return super.queryByNameParamSql(sb.toString() ,null, values.toArray(),adv,adv.getClass());
	}

	@SuppressWarnings("unchecked")
	public List<TAdv> queryAll(){
		StringBuffer sb = new StringBuffer(" from TAdv where isShow =1 ");
		return super.getByPropertyHql(sb.toString(), null, TAdv.class);
	}
	
	@Override
	public Integer updateFieidById(String fieid, Integer value, String ids) {
		String sql = "update t_adv set "+fieid+"= :fieid where id in (:ids)";
		return super.executeSQL(sql, new String[]{"fieid","ids"}, new Object[]{value,ids});
	}
	
	@SuppressWarnings("rawtypes")
	public Integer getCountByApId(Integer apId){
		StringBuffer sql = new StringBuffer("select count(0) from t_adv where ap_id=? ");
		List list = super.getByPropertySql(sql.toString(), new Object[]{apId});
		return Integer.parseInt(list.get(0).toString());
	}

	@SuppressWarnings("unchecked")
	public List<TAdv> getAdvByApId(Integer apId){
		StringBuffer sb = new StringBuffer(" select a.id,  ");
		sb.append(" a.ap_id as apId, ");
		sb.append(" a.adv_title as advTitle , ");
		sb.append(" a.adv_link as advLink, ");
		sb.append(" a.adv_content as advContent, ");
		sb.append(" a.is_show as isShow, ");
		sb.append(" a.starttime, ");
		sb.append(" a.endtime, ");
		sb.append(" a.slide_sort as slideSort, ");
		sb.append(" a.click_num as clickNum, ");
		sb.append(" a.adv_price as advPrice, ");
		sb.append(" a.createtime as createtime ");
		sb.append(" from t_adv a where ap_id=? and is_show = 1 ");
		List<TAdv> list = super.getByPropertySql(sb.toString(), new Object[]{apId},TAdv.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TAdv> getGroupApidListByIds(String ids){
		StringBuffer sb = new StringBuffer(" select a.ap_id as apId,  ");
		sb.append(" b.adv_num as advNum, ");
		sb.append(" count(1) as countNum ");
		sb.append(" from t_adv a , t_adv_position b where a.ap_id = b.id ");
		sb.append("  and a.id in (:ids) ");
		sb.append("  group by a.ap_id,b.adv_num ");
		return super.getByNameParamSql(sb.toString(), new String[] { "ids" }, new Object[] { ids.split(",") }, TAdv.class);
	}


}
