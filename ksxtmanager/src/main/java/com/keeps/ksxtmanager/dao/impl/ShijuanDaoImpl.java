package com.keeps.ksxtmanager.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.ksxtmanager.dao.ShijuanDao;
import com.keeps.ksxtmanager.model.KsShijuan;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ShijuanDao.java</p>  
 * <p>Description: 试卷管理DAO接口实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年10月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class ShijuanDaoImpl extends AbstractDao implements ShijuanDao {
	
	public Page queryList(KsShijuan shijuan){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,b.name as shijuantypename,c.name as degofdifftypename,d.name as empname "
				+ "  from ks_shijuan a "
				+ " left join t_dict_type b on a.shijuantype = b.id "
				+ " left join t_dict_type c on a.degofdifftype = c.id "
				+ " left join t_emp d on a.createperson = d.id ");
		sb.append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(shijuan.getTitle())) {
			sb.append(" and a.title like ? ");
			values.add("%"+shijuan.getTitle().trim()+"%");
		}
		if (shijuan.getShijuantype()!=null && 0!=shijuan.getShijuantype()) {
			sb.append(" and (b.id = ? or b.pid = ?) ");
			values.add(shijuan.getShijuantype());
			values.add(shijuan.getShijuantype());
		}

		if (shijuan.getKaoshitype()!=null) {
			sb.append(" and a.kaoshitype = ? ");
			values.add(shijuan.getKaoshitype());
		}
		if (shijuan.getDegofdifftype()!=null && 0!=shijuan.getDegofdifftype()) {
			sb.append(" and a.degofdifftype = ? ");
			values.add(shijuan.getDegofdifftype());
		}
		if(StringUtils.hasText(shijuan.getSidx()) && StringUtils.hasText(shijuan.getSord())) {
			if ("shijuantypename".equals(shijuan.getSidx())) {
				sb.append("order by b.name "+shijuan.getSord());
			}else if ("degofdifftypename".equals(shijuan.getSidx())){
				sb.append("order by c.name "+shijuan.getSord());
			}else if ("empname".equals(shijuan.getSidx())){
				sb.append("order by d.name "+shijuan.getSord());
			}else{
				sb.append("order by a."+shijuan.getSidx()+" "+shijuan.getSord());
			}
		}else{
			sb.append(" order by a.updatetime desc ");	
		}
		return super.queryBySql(sb.toString(), values.toArray(), shijuan, KsShijuan.class);
	}
	
	/**
	 * 查询未考试卷
	 */
	public Page querywksjList(KsShijuan shijuan){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,date_format(a.starttime,'%Y-%m-%d %H:%m') as starttimestr,date_format(a.endtime,'%Y-%m-%d %H:%m') as endtimestr, "
				+ " b.name as shijuantypename,c.name as degofdifftypename "
				+ " from ( SELECT a.* FROM ks_shijuan a INNER JOIN ks_shijuan_emp b ON a.id = b.shijuanid AND b.empid = ? "
				+ " 		UNION ALL "
				+ " 	SELECT a.* FROM ks_shijuan a INNER JOIN ks_shijuan_org c on a.id = c.shijuanid and c.orgid = ? "
				+ " 		UNION ALL "
				+ " SELECT a.* FROM ks_shijuan a where a.shijuantype = 3 ) a ");
		sb.append(" left join t_dict_type b on a.shijuantype = b.id left join t_dict_type c on a.degofdifftype = c.id "
				+ " where a.id NOT in (select a.shijuanid from ks_shijuan_copy a where a.createperson = ?) ");
		List<Object> values = new ArrayList<Object>();
		values.add(shijuan.getEmpid());
		values.add(shijuan.getOrgid());
		values.add(shijuan.getEmpid());
		if (StringUtils.hasText(shijuan.getTitle())) {
			sb.append(" and a.title like ? ");
			values.add("%"+shijuan.getTitle().trim()+"%");
		}
		if (shijuan.getKaoshitype()!=null) {
			sb.append(" and a.kaoshitype = ? ");
			values.add(shijuan.getKaoshitype());
		}
		if (shijuan.getDegofdifftype()!=null && 0!=shijuan.getDegofdifftype()) {
			sb.append(" and a.degofdifftype = ? ");
			values.add(shijuan.getDegofdifftype());
		}
		if(StringUtils.hasText(shijuan.getSidx()) && StringUtils.hasText(shijuan.getSord())) {
			if ("shijuantypename".equals(shijuan.getSidx())) {
				sb.append("order by b.name "+shijuan.getSord());
			}else if ("degofdifftypename".equals(shijuan.getSidx())){
				sb.append("order by c.name "+shijuan.getSord());
			}else if ("startendtime".equals(shijuan.getSidx())){
				sb.append("order by a.starttime "+shijuan.getSord());
			}else{
				sb.append("order by a."+shijuan.getSidx()+" "+shijuan.getSord());
			}
		}else{
			sb.append(" order by a.updatetime desc ");	
		}
		return super.queryBySql(sb.toString(), values.toArray(), shijuan, KsShijuan.class);
	}

	public KsShijuan getById(Integer id){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,date_format(a.starttime,'%Y-%m-%d %H:%m') as starttimestr,date_format(a.endtime,'%Y-%m-%d %H:%m') as endtimestr,"
				+ " b.name as shijuantypename,c.name as degofdifftypename "
				+ "  from ks_shijuan a "
				+ " left join t_dict_type b on a.shijuantype = b.id "
				+ " left join t_dict_type c on a.degofdifftype = c.id "
				+ " where a.id = ? ");
		List<Object> values = new ArrayList<Object>();
		values.add(id);
		List<KsShijuan> list = super.getByPropertySql(sb.toString(), values.toArray(), KsShijuan.class);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	@Override
	public Integer deleteShijuanShitiByShijuanId(String shijuanids) {
		return super.executeSQL(" delete from ks_shijuan_shiti where shijuanid in (:shijuanid)", new String[]{"shijuanid"}, new Object[]{shijuanids.split(",")});
	}

	@Override
	public Integer deleteShijuanOrgByShijuanId(String shijuanids) {
		return super.executeSQL(" delete from ks_shijuan_org where shijuanid in (:shijuanid)", new String[]{"shijuanid"}, new Object[]{shijuanids.split(",")});
	}

	@Override
	public Integer deleteShijuanEmpByShijuanId(String shijuanids) {
		return super.executeSQL(" delete from ks_shijuan_emp where shijuanid in (:shijuanid)", new String[]{"shijuanid"}, new Object[]{shijuanids.split(",")});
	}

}
