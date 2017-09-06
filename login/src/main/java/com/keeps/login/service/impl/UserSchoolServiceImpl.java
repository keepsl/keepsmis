package com.keeps.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.login.dao.UserSchoolDao;
import com.keeps.login.service.UserSchoolService;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.UserSchool;

@Service("userSchoolService")
public class UserSchoolServiceImpl extends AbstractService implements UserSchoolService {

	@Autowired
	private UserSchoolDao dao;
	
	public UserSchool getUserSchool(String id) {
		if(StringUtils.notText(id)){
			return new UserSchool();
		}
		return dao.getUserSchool(id);
	}
	
	@Override
	public boolean hasAuthorizition(Integer userid,String targetUrl){
		Assert.isTrue(StringUtils.hasText(targetUrl), "验证URL不能为空.");
		//判断菜单是否存在，不存在，不验证
		Integer count = dao.countMenuUrlByUrl(targetUrl);
		count = dao.countMethodUrlByUrl(targetUrl);
		System.out.println(count+"==="+targetUrl);
		if(count<=0){//菜单和操作不存在，不进行验证
			return true;
		}
		count = dao.countUrlByRole(userid, targetUrl);
		if (count>0) {
			return true;
		}else{
			return false;
		}
	}
}
