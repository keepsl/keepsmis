package com.keeps.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.service.AbstractService;
import com.keeps.login.dao.UserSchoolDao;
import com.keeps.login.service.UserSchoolService;
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
}
