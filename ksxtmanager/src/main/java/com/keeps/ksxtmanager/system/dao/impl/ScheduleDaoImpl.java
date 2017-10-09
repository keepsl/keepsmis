package com.keeps.ksxtmanager.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.ksxtmanager.system.dao.ScheduleDao;
import com.keeps.model.TNews;
import com.keeps.model.TSchedule;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ScheduleDaoImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年7月1日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class ScheduleDaoImpl extends AbstractDao implements ScheduleDao {

	@Override
	public Page query(TSchedule schedule) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*, date_format(a.scheduletime,'%Y-%m-%d %H:%i') as scheduletimestr from t_schedule a ");
		sb.append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (schedule.getStatus()!=null) {
			if (schedule.getStatus().intValue()==-1) {
				sb.append(" and a.status in (1,2) ");
			}else{
				sb.append(" and a.status=? ");
				values.add(schedule.getStatus());
			}
		}
		if (StringUtils.hasText(schedule.getTitle())) {
			sb.append(" and a.title like ? ");
			values.add("%"+schedule.getTitle().trim()+"%");
		}
		if (StringUtils.hasText(schedule.getScheduletimesta())) {//任务开始日
			sb.append(" and date_format(a.scheduletime,'%Y-%m-%d') >= str_to_date(?,'%Y-%m-%d') ");
			values.add(schedule.getScheduletimesta().trim());
		}
		if (StringUtils.hasText(schedule.getScheduletimeend())) {//任务结束日
			sb.append(" and date_format(a.scheduletime,'%Y-%m-%d') <= str_to_date(?,'%Y-%m-%d') ");
			values.add(schedule.getScheduletimeend().trim());
		}
		if(StringUtils.hasText(schedule.getSidx()) && StringUtils.hasText(schedule.getSord())) {
			sb.append("order by a."+schedule.getSidx()+" "+schedule.getSord());
		}else{
			sb.append(" order by a.scheduletime desc ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), schedule, TSchedule.class);
	}

	@Override
	public TNews getById(Integer id) {
		return null;
	}

}
