package com.keeps.crm.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TContactRecord;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ContactRecordDao.java</p>  
 * <p>Description: 客户联系记录DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ContactRecordDao extends SoftDao{

	public Page queryList(TContactRecord contactRecord);
	
	public Page queryStreamList(TContactRecord contactRecord,boolean isadmin);
	
	public Page queryStatisticsList(TContactRecord contactRecord,boolean isadmin);

	
}
