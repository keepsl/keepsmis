package com.keeps.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.crm.dao.ContactRecordDao;
import com.keeps.crm.service.ContactRecordService;
import com.keeps.model.TContactRecord;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;

/** 
 * <p>Title: ContactRecordServiceImpl.java</p>  
 * <p>Description: 客户联系记录Service实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ContactRecordServiceImpl extends AbstractService implements ContactRecordService {

	@Autowired
	private ContactRecordDao contactRecordDao;
	
	@Override
	public Page queryList(TContactRecord contactRecord) {
		return contactRecordDao.queryList(contactRecord);
	}

	public Page queryStreamList(TContactRecord contactRecord){
		if (!UserSchoolThread.get().isSuperAdmin()) {
			contactRecord.setEmpid(UserSchoolThread.get().getUserid());
		}
		return contactRecordDao.queryStreamList(contactRecord, UserSchoolThread.get().isSuperAdmin());
	}

	@Override
	public String saveOrUpdate(TContactRecord contactRecord) {
		Assert.isTrue(contactRecord.getClientid()!=null, "客户id不能为空!");
		if (StringUtils.notText(contactRecord.getUpdatetimestr())) {
			contactRecord.setUpdatetimestr(DateUtils.formatNow());
		}
		Assert.isTrue(StringUtils.hasText(contactRecord.getUpdatetimestr()), "联系时间不允许为空!");
		Assert.isTrue(StringUtils.hasText(contactRecord.getRemark()), "沟通内容不允许为空!");
		if (StringUtils.notText(contactRecord.getNexttime())) {
			contactRecord.setNexttime(null);
		}
		contactRecord.setEmpid(UserSchoolThread.get().getUserid());
		contactRecord.setUpdatetime(DateUtils.parse("yyyy-MM-dd HH:mm:ss", contactRecord.getUpdatetimestr()));
		super.save(contactRecord);
		return null;
	}

}
