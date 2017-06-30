package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.AdvPositionDao;
import com.keeps.model.TAdvPosition;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvPositionDaoImpl.java</p>  
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
public class AdvPositionDaoImpl extends AbstractDao implements AdvPositionDao {

	@Override
	public Page queryList(TAdvPosition advPosition) {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" select a.id,  ");
		sql.append("  ap_name as apName,  ");
		sql.append("  ap_intro as apIntro,  ");
		sql.append("  ap_class as apClass,  ");
		sql.append("  ap_display as apDisplay,  ");
		sql.append("  is_show as isShow,  ");
		sql.append("  ap_width as apWidth,  ");
		sql.append("  ap_height as apHeight,  ");
		sql.append("  ap_price as apPrice,  ");
		sql.append("  adv_num as advNum,  ");
		sql.append("  click_num as clickNum,  ");
		sql.append("  default_content as defaultContent,  ");
		sql.append("  is_cache as isCache, ");
		sql.append("  createtime,  ");
		sql.append("  updatetime,  ");
		sql.append("  createperson  ");
		sql.append("  from t_adv_position a ");
		if (StringUtils.hasText(advPosition.getApName())) {
			sql.append(" where a.ap_name like ? ");
			values.add("%"+advPosition.getApName().trim()+"%");
		}
		return super.queryByNameParamSql(sql.toString() ,null, values.toArray(),advPosition,advPosition.getClass());
	}

	@SuppressWarnings("unchecked")
	public List<TAdvPosition> queryAll(){
		StringBuffer sql = new StringBuffer(" from TAdvPosition where isShow = 1 ");
		return super.getByPropertyHql(sql.toString(), null, TAdvPosition.class);
	}
	
	@Override
	public Integer updateFieidById(String fieid, Integer value, String ids) {
		String sql = "update t_adv_position set "+fieid+"= :fieid where id in (:ids)";
		return super.executeSQL(sql, new String[]{"fieid","ids"}, new Object[]{value,ids});
	}
	

}
