package com.keeps.crm.service.impl;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.crm.dao.BuyRecordDao;
import com.keeps.crm.service.BuyRecordService;
import com.keeps.model.TBuyRecord;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;

/** 
 * <p>Title: BuyRecordServiceImpl.java</p>  
 * <p>Description: 客户购买记录Service实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class BuyRecordServiceImpl extends AbstractService implements BuyRecordService {
	
	@Autowired
	private BuyRecordDao buyRecordDao;

	@Override
	public Page queryList(TBuyRecord buyRecord) {
		return buyRecordDao.queryList(buyRecord,UserSchoolThread.get().isSuperAdmin());
	}
	
	@Override
	public Page queryStreamList(TBuyRecord buyRecord){
		if (!UserSchoolThread.get().isSuperAdmin()) {
			buyRecord.setEmpid(UserSchoolThread.get().getUserid());
		}
		return buyRecordDao.queryStreamList(buyRecord,UserSchoolThread.get().isSuperAdmin());
	}

	public Page queryStatisticsList(TBuyRecord buyRecord){
		if (!UserSchoolThread.get().isSuperAdmin()) {
			buyRecord.setEmpid(UserSchoolThread.get().getUserid());
		}
		return buyRecordDao.queryStatisticsList(buyRecord,UserSchoolThread.get().isSuperAdmin());
	}

	
	@Override
	public String saveOrUpdate(TBuyRecord buyRecord) {
		Assert.isTrue(buyRecord.getClientid()!=null, "客户id不能为空!");
		if (StringUtils.notText(buyRecord.getUpdatetimestr())) {
			buyRecord.setUpdatetimestr(DateUtils.formatNow());
		}
		Assert.isTrue(StringUtils.hasText(buyRecord.getProductname()), "产品名称不允许为空!");
		String[] nameprices = buyRecord.getProductname().split("￥");
		buyRecord.setProductname(nameprices[0]);
		Assert.isTrue(buyRecord.getBuynum()!=null, "数量不能为空");
		Assert.isTrue(buyRecord.getBuynum()>0, "数量不能小于0");
		//Assert.isTrue(buyRecord.getPrice()!=null, "产品价格不允许为空!");
		DecimalFormat df = new DecimalFormat("#.##");
		try {
			buyRecord.setPrice(Float.parseFloat(nameprices[1]));
			buyRecord.setTotalprice(Float.parseFloat(df.format(buyRecord.getPrice()*buyRecord.getBuynum())));
		} catch (NumberFormatException e) {
			throw new CapecException("");
		}
		Assert.isTrue(StringUtils.hasText(buyRecord.getUpdatetimestr()), "购买时间不允许为空!");
		buyRecord.setEmpid(UserSchoolThread.get().getUserid());
		buyRecord.setUpdatetime(DateUtils.parse("yyyy-MM-dd HH:mm", buyRecord.getUpdatetimestr()));
		//buyRecord.setClientid(clientid);
		super.save(buyRecord);
		return null;
	}
	
}
