package com.keeps.ksxtmanager.system.dao;

import java.util.List;
import java.util.Map;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TEmp;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: EmpDao.java</p>  
 * <p>Description: 员工管理DAO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface EmpDao extends SoftDao{
	
	public Page queryList(TEmp emp);
	
	public List<TEmp> getList(TEmp emp);
	
	public List<TEmp> getFzridsByClientid(Integer clientid);

	public Integer countByPwdEmpid(Integer id,String pwd);
	
	public TEmp getById(Integer id);
	
	public String getMaxJobnumber();
	
	public Integer deleteEmproleByEmpid(Integer empid);
	
	public Integer deleteEmproleByEmpids(String empids);

	public Integer deleteEmpclientByEmpids(String empids);
	
	public Integer countByOrgids(String orgids);
	
	public List<Map<String, Object>> getClientGroupByEmp();
	
	public List<Map<String, Object>> getContactGroupByEmp(String contacttimesta,String contacttimeend);

	
}
