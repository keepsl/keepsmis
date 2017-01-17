package com.keeps.login.service;

import com.keeps.core.service.SoftService;
import com.keeps.tools.utils.UserSchool;

public interface UserSchoolService extends SoftService{
	
	UserSchool getUserSchool(String id);

}
