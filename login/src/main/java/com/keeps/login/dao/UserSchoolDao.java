package com.keeps.login.dao;

import com.keeps.core.dao.SoftDao;
import com.keeps.tools.utils.UserSchool;

public interface UserSchoolDao extends SoftDao {
	
	public UserSchool getUserSchool(String id);
	
	public Integer countUrlByRole(Integer userid,String url);
	
	public Integer countMethodUrlByUrl(String code);

	public Integer countMenuUrlByUrl(String url);

}
