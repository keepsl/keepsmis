package com.keeps.crm.service;

import com.keeps.core.service.SoftService;
import com.keeps.model.TBuyRecord;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: BuyRecord.java</p>  
 * <p>Description: 客户购买记录Service接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface BuyRecordService extends SoftService{

	public Page queryList(TBuyRecord buyRecord);
	
	public Page queryStreamList(TBuyRecord buyRecord);
	
	public Page queryStatisticsList(TBuyRecord buyRecord);

	public String saveOrUpdate(TBuyRecord buyRecord);
	
}
