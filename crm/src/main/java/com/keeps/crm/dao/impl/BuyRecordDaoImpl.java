package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.BuyRecordDao;
import com.keeps.model.TBuyRecord;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: BuyRecordDaoImpl.java</p>  
 * <p>Description: 客户购买记录DAO实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class BuyRecordDaoImpl extends AbstractDao implements BuyRecordDao {
	
	public Page queryList(TBuyRecord buyRecord,boolean isadmin){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,b.name as empname from t_buy_record a left join t_emp b on a.empid = b.id ");
		sb.append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(buyRecord.getEmpname())) {
			sb.append(" and (b.name like ? or b.pym like ? or b.jobnumber like ? ) ");
			values.add("%"+buyRecord.getEmpname().trim()+"%");
			values.add("%"+buyRecord.getEmpname().trim()+"%");
			values.add("%"+buyRecord.getEmpname().trim()+"%");
		}
		if (buyRecord.getClientid()!=null) {
			sb.append(" and a.clientid = ? ");
			values.add(buyRecord.getClientid());
		}
		if (StringUtils.hasText(buyRecord.getUpdatetimestr())) {
			sb.append(" and a.buytime = ? ");
			values.add(DateUtils.format(buyRecord.getUpdatetimestr(), "yyyy-MM-dd"));
		}
		if(StringUtils.hasText(buyRecord.getSidx()) && StringUtils.hasText(buyRecord.getSord())) {
			if (buyRecord.getSidx().equals("name")) {
	        	sb.append("order by b.name "+buyRecord.getSord());
			}else{
	        	sb.append("order by a."+buyRecord.getSidx()+" "+buyRecord.getSord());
			}
		}else{
			sb.append("  order by a.updatetime desc ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), buyRecord, TBuyRecord.class);
	}

	public Page queryStreamList(TBuyRecord buyRecord,boolean isadmin){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.id,b.name as clientname,b.phone as clientphone,a.productname,a.price,c.name as empname,a.updatetime,a.createtime,a.remark from t_buy_record a inner join t_client b on a.clientid = b.id ");
		sb.append(" left join t_emp c on a.empid = c.id ");
		sb.append(" where 1 = 1 ");
		List<Object> values = new ArrayList<Object>();
		if (!isadmin) {
			sb.append(" and c.id = ? ");
			values.add(buyRecord.getEmpid());
		}else{
			if (buyRecord.getEmpid()!=null) {
				sb.append(" and c.id = ? ");
				values.add(buyRecord.getEmpid());
			}
		}
		
		if (StringUtils.hasText(buyRecord.getClientname())) {
			sb.append(" and (b.name like ? or b.pym like ? ) ");
			values.add("%"+buyRecord.getClientname().trim()+"%");
			values.add("%"+buyRecord.getClientname().trim()+"%");
		}
		if (StringUtils.hasText(buyRecord.getClientphone())) {
			if (buyRecord.getClientphone().length()==4) {
				sb.append(" and right(b.phone,4) = ? ");
				values.add(buyRecord.getClientphone().trim());	
			}else{
				sb.append(" and b.phone like ? ");
				values.add("%"+buyRecord.getClientphone().trim()+"%");
			}
		}
		if (StringUtils.hasText(buyRecord.getBuytimesta())) {
			sb.append(" and date_format(a.updatetime,'%Y-%m-%d') >= str_to_date(?,'%Y-%m-%d') ");
			values.add(DateUtils.format(buyRecord.getBuytimesta(), "yyyy-MM-dd"));
		}
		if (StringUtils.hasText(buyRecord.getBuytimeend())) {
			sb.append(" and date_format(a.updatetime,'%Y-%m-%d') <= str_to_date(?,'%Y-%m-%d') ");
			values.add(DateUtils.format(buyRecord.getBuytimeend(), "yyyy-MM-dd"));
		}
		if(StringUtils.hasText(buyRecord.getSidx()) && StringUtils.hasText(buyRecord.getSord())) {
			if (buyRecord.getSidx().equals("clientname")) {
	        	sb.append("order by b.name "+buyRecord.getSord());
			}else if (buyRecord.getSidx().equals("clientphone")) {
	        	sb.append("order by b.phone "+buyRecord.getSord());
			}else if (buyRecord.getSidx().equals("empname")) {
	        	sb.append("order by c.name "+buyRecord.getSord());
			}else{
	        	sb.append("order by a."+buyRecord.getSidx()+" "+buyRecord.getSord());
			}
		}else{
			sb.append("  order by a.updatetime desc ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), buyRecord, TBuyRecord.class);
	}

	
}
