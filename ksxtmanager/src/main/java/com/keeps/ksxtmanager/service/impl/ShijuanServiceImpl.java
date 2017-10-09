package com.keeps.ksxtmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.ksxtmanager.dao.ShijuanDao;
import com.keeps.ksxtmanager.model.KsShijuan;
import com.keeps.ksxtmanager.model.KsShijuanEmp;
import com.keeps.ksxtmanager.model.KsShijuanOrg;
import com.keeps.ksxtmanager.model.KsShijuanShiti;
import com.keeps.ksxtmanager.service.ShijuanService;
import com.keeps.ksxtmanager.system.service.EmpService;
import com.keeps.ksxtmanager.system.service.OrgService;
import com.keeps.model.TEmp;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;

/** 
 * <p>Title: ShijuanServiceImpl.java</p>  
 * <p>Description: 试卷管理SERVICE接口实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年10月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ShijuanServiceImpl extends AbstractService implements ShijuanService {

	@Autowired
	private ShijuanDao shijuanDao;
	@Autowired
	private EmpService empService;
	
	public Page queryList(KsShijuan shijuan){
		return shijuanDao.queryList(shijuan);
	}

	public Page querywksjList(KsShijuan shijuan){
		shijuan.setEmpid(UserSchoolThread.get().getUserid());
		TEmp emp = empService.getById(shijuan.getEmpid());
		shijuan.setOrgid(emp.getOrgid());
		return shijuanDao.querywksjList(shijuan);
	}

	@Override
	public String saveOrUpdate(KsShijuan shijuan) {
		Assert.isTrue(StringUtils.hasText(shijuan.getTitle()), "试卷标题不能为空.");
		Assert.isTrue(shijuan.getShijuantype()!=null, "试卷分类不能为空!");
		Assert.isTrue(shijuan.getDegofdifftype()!=null, "难易度不能为空!");
		Assert.isTrue(shijuan.getTimelength()!=null, "答题时长不能为空!");
		Assert.isTrue(shijuan.getStatus()!=null, "状态不能为空!");
		Assert.isTrue(StringUtils.hasText(shijuan.getStarttimestr()), "发布开始时间不能为空!");
		Assert.isTrue(StringUtils.hasText(shijuan.getEndtimestr()), "发布结束时间不能为空!");
		Assert.isTrue(shijuan.getKaoshitype()!=null, "考试方式不能为空!");
		if (shijuan.getKaoshitype().intValue()==1) {
			Assert.isTrue(StringUtils.hasText(shijuan.getOrgids()), "考试部门不能为空!");
			shijuan.setEmpids("");
		}else if(shijuan.getKaoshitype().intValue()==2){
			Assert.isTrue(StringUtils.hasText(shijuan.getEmpids()), "考试人员不能为空!");
			shijuan.setOrgids("");
		}
		Assert.isTrue(StringUtils.hasText(shijuan.getQuestionsids()), "试题不能为空!");
		shijuan.setStarttime(DateUtils.parse(shijuan.getStarttimestr(), "yyyy-MM-dd HH:mm"));
		shijuan.setEndtime(DateUtils.parse(shijuan.getEndtimestr(), "yyyy-MM-dd HH:mm"));
		shijuan.setZongfen(100);
		super.saveOrUpdateEntity(shijuan, EditType.NULL_UN_UPDATE);

		shijuanDao.deleteShijuanShitiByShijuanId(String.valueOf(shijuan.getId()));
		shijuanDao.deleteShijuanOrgByShijuanId(String.valueOf(shijuan.getId()));
		shijuanDao.deleteShijuanEmpByShijuanId(String.valueOf(shijuan.getId()));

		//保存试题
		String questionsids[] = shijuan.getQuestionsids().split(",");
		for (String questionsid : questionsids) {
			KsShijuanShiti shijuanShiti = new KsShijuanShiti();
			shijuanShiti.setCreateperson(UserSchoolThread.get().getUserid());
			shijuanShiti.setCreatetime(DateUtils.getNow());
			shijuanShiti.setFenshu(1);
			shijuanShiti.setShijuanid(shijuan.getId());
			shijuanShiti.setShitiid(Integer.parseInt(questionsid));
			super.save(shijuanShiti);
		}
		if (shijuan.getKaoshitype().intValue()==1) {
			//保存 部门
			String orgids[] = shijuan.getOrgids().split(",");
			for (String orgid : orgids) {
				KsShijuanOrg shijuanOrg = new KsShijuanOrg();
				shijuanOrg.setOrgid(Integer.parseInt(orgid));
				shijuanOrg.setShijuanid(shijuan.getId());
				super.save(shijuanOrg);
			}
		}else if(shijuan.getKaoshitype().intValue()==2){
			//保存人员
			String  empids[] = shijuan.getEmpids().split(",");
			for (String empid : empids) {
				KsShijuanEmp shijuanEmp = new KsShijuanEmp();
				shijuanEmp.setEmpid(Integer.parseInt(empid));
				shijuanEmp.setShijuanid(shijuan.getId());
				super.save(shijuanEmp);
			}
		}
		return null;
	}

	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(KsShijuan.class, ids, IdTypes.Integer);
		shijuanDao.deleteShijuanShitiByShijuanId(ids);
		shijuanDao.deleteShijuanOrgByShijuanId(ids);
		shijuanDao.deleteShijuanEmpByShijuanId(ids);
		return "删除成功!";
	}

	@Override
	public KsShijuan getById(Integer id) {
		KsShijuan shijuan = shijuanDao.getById(id);
		return shijuan;
	}
	
	
}
