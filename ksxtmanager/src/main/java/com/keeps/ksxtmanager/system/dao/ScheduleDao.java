package com.keeps.ksxtmanager.system.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TNews;
import com.keeps.model.TSchedule;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ScheduleDao.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年7月1日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ScheduleDao extends SoftDao{

	public Page query(TSchedule schedule);
	
	public TNews getById(Integer id);


}
