package com.keeps.ksxtmanager.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.ksxtmanager.model.KsShijuan;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ShijuanDao.java</p>  
 * <p>Description: 试卷管理DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年10月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ShijuanDao extends SoftDao{

	public Page queryList(KsShijuan shijuan);
	
	public Page querywksjList(KsShijuan shijuan);
	
	public KsShijuan getById(Integer id);
	
	public Integer deleteShijuanShitiByShijuanId(String shijuanids);
	
	public Integer deleteShijuanOrgByShijuanId(String shijuanids);
	
	public Integer deleteShijuanEmpByShijuanId(String shijuanids);


}
