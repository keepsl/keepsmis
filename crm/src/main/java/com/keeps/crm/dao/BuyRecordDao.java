package com.keeps.crm.dao;

import com.keeps.model.TBuyRecord;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: BuyRecordDao.java</p>  
 * <p>Description: 客户购买记录DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface BuyRecordDao {
	
	public Page queryList(TBuyRecord buyRecord,boolean isadmin);

	public Page queryStreamList(TBuyRecord buyRecord,boolean isadmin);
	
	public Page queryStatisticsList(TBuyRecord buyRecord,boolean isadmin);

	
}
