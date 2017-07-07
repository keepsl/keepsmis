package com.keeps.crm.service;

import com.keeps.core.service.SoftService;
import com.keeps.model.TContactRecord;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ContactRecordService.java</p>  
 * <p>Description: 客户联系记录Service接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ContactRecordService extends SoftService{

	public Page queryList(TContactRecord contactRecord);
	
	public Page queryStreamList(TContactRecord contactRecord);
	
	public Page queryStatisticsList(TContactRecord contactRecord);
	
	public String saveOrUpdate(TContactRecord contactRecord);
	
}
