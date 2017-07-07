package com.keeps.crm.service;

import com.keeps.core.service.SoftService;
import com.keeps.model.TSchedule;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ScheduleService.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年7月1日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ScheduleService extends SoftService{

	public Page query(TSchedule schedule);
	
	public TSchedule getById(Integer id);
	
	public String saveOrUpdate(TSchedule schedule);
	
	public String delete(String ids);

}
