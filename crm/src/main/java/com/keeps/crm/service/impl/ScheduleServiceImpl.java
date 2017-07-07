package com.keeps.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.crm.dao.ScheduleDao;
import com.keeps.crm.service.ScheduleService;
import com.keeps.model.TSchedule;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ScheduleServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年7月1日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ScheduleServiceImpl extends AbstractService implements ScheduleService {

	@Autowired
	private ScheduleDao scheduleDao;
	
	@Override
	public Page query(TSchedule schedule) {
		return scheduleDao.query(schedule);
	}

	@Override
	public TSchedule getById(Integer id) {
		return super.get(TSchedule.class, id);
	}

	@Override
	public String saveOrUpdate(TSchedule schedule) {
		Assert.isTrue(StringUtils.hasText(schedule.getTitle()), "日程标题不能为空！");
		Assert.isTrue(StringUtils.hasText(schedule.getScheduletimestr()), "日程日期不能为空！");
		if (schedule.getStatus()==null) {
			schedule.setStatus(1);
		}
		schedule.setScheduletime(DateUtils.parse(schedule.getScheduletimestr(), "yyyy-MM-dd HH:mm"));
		super.saveOrUpdateEntity(schedule, EditType.NULL_UN_UPDATE);
		return null;
	}

	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(TSchedule.class, ids, IdTypes.Integer);
		return "删除成功!";
	}

}
