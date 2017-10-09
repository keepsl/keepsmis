package com.keeps.ksxtmanager.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.ksxtmanager.system.dao.EmpDao;
import com.keeps.ksxtmanager.system.dao.OrgDao;
import com.keeps.ksxtmanager.system.service.EmpService;
import com.keeps.ksxtmanager.utils.Hanyu;
import com.keeps.model.TEmp;
import com.keeps.model.TEmprole;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.CalendarUtil;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.MD5Util;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;
import com.keeps.utils.TreeNode;
import com.keeps.utils.ValidateUtil;

/** 
 * <p>Title: EmpServiceImpl.java</p>  
 * <p>Description: 员工管理Service实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class EmpServiceImpl extends AbstractService implements EmpService{

	@Autowired
	private EmpDao empDao;
	@Autowired
	private OrgDao orgDao;
	
	@Override
	public Page queryList(TEmp emp) {
		Page page = empDao.queryList(emp);
		return page;
	}

	@Override
	public List<TEmp> getList(TEmp emp) {
		return empDao.getList(emp);
	}

	@Override
	public TEmp getById(Integer id) {
		return empDao.getById(id);
	}

	public String updatePwdById(Integer id){
		Assert.isTrue(id!=null, "id不能为空!");
		TEmp emp = new TEmp();
		emp.setId(id);
		emp.setPassword(MD5Util.getInstance().toMD5("888888"));
		super.update(emp,EditType.NULL_UN_UPDATE);
		return null;
	}
	
	public String updatePwd(String oldPwd,String newPwd,String checkNewPwd){
		Assert.isTrue(StringUtils.hasText(oldPwd), "原密码不能为空!");
		Assert.isTrue(StringUtils.hasText(newPwd), "新密码不能为空!");
		Assert.isTrue(StringUtils.hasText(checkNewPwd), "确认密码不能为空!");
		if (!newPwd.equals(checkNewPwd)) {
			throw new CapecException("新密码与确认密码不一致,请确认后重新提交!");
		}
		Assert.isTrue(newPwd.length()>=6, "新密码必须由6-15位字母、数字组成!");
		Integer count = empDao.countByPwdEmpid(UserSchoolThread.get().getUserid(), MD5Util.getInstance().toMD5(oldPwd));
		if (count<=0) {
			throw new CapecException("原密码不正确,请确认后重新提交!");
		}
		TEmp emp = new TEmp();
		emp.setId(UserSchoolThread.get().getUserid());
		emp.setPassword(MD5Util.getInstance().toMD5(newPwd));
		super.update(emp,EditType.NULL_UN_UPDATE);
		return null;
		
	}

	@Override
	public String saveOrUpdate(TEmp emp) {
		Assert.isTrue(StringUtils.hasText(emp.getName()), "员工姓名不能为空!");
		Assert.isTrue(emp.getOrgid()!=null&&emp.getOrgid().intValue()!=0, "所属组织不能为空!");
/*		Assert.isTrue(StringUtils.hasText(emp.getRoleids()), "所属角色不能为空!");
*/		Assert.isTrue(StringUtils.hasText(emp.getPhone()), "手机号不能为空!");
		Assert.isTrue(ValidateUtil.isMobile(emp.getPhone()), "手机号格式不正确!");
		if (StringUtils.hasText(emp.getEmail())) {
			Assert.isTrue(ValidateUtil.isEmail(emp.getEmail()), "邮箱格式不正确!");
		}
		Assert.isTrue(emp.getSex()!=null, "性别不能为空!");
		Assert.isTrue(emp.getStatus()!=null, "员工状态不能为空!");
		if (emp.getIslunar()==null) {//是否农历，1是，2否
			emp.setIslunar(1);
		}
		if (StringUtils.hasText(emp.getBirthdate())) {
			if (emp.getIslunar().intValue()==1) {//是农历，把生日转换成阳历
				emp.setSolarbirthdate(CalendarUtil.solarToLunar(emp.getBirthdate()));
			}else{
				emp.setSolarbirthdate(emp.getBirthdate());
			}
		}else{
			emp.setBirthdate(null);
			emp.setSolarbirthdate(null);
		}
		if (emp.getId()==null) {
			emp.setPassword(MD5Util.getInstance().toMD5("888888"));
			String jobnumber = empDao.getMaxJobnumber();
			jobnumber = String.format("%06d", Integer.parseInt(jobnumber)+1);
			emp.setJobnumber(jobnumber);
			emp.setIsadmin(0);
		}
		if(!empDao.isUnique(emp, new String[]{"phone"})){
			throw new CapecException("["+emp.getPhone()+"]手机号码已经存在,不允许重复添加!");
		}
		emp.setPym(Hanyu.getFirstSpell(emp.getName()));
		emp.setAllpym(Hanyu.getFullSpell(emp.getName()));
		super.saveOrUpdateEntity(emp,EditType.NULL_UN_UPDATE);
		
		if (emp.getId()!=null) {
			empDao.deleteEmproleByEmpid(emp.getId());
		}
		if (StringUtils.hasText(emp.getRoleids())) {
			String[] roleids = emp.getRoleids().split(",");
			for (String roleid : roleids) {
				TEmprole emprole = new TEmprole();
				emprole.setRoleid(Integer.parseInt(roleid.trim()));
				emprole.setEmpid(emp.getId());
				super.save(emprole);
			}
		}
		return null;
	}

	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(TEmp.class, ids, IdTypes.Integer);
		//删除关联的角色
		empDao.deleteEmproleByEmpids(ids);
		return "删除成功!";
	}

	@Override
	public String updateFieidById(String fieid, Integer value, String ids) {
		return null;
	}

	public List<TreeNode> getEmpTree(String empids){
		List<TreeNode> newlistnode = new ArrayList<>();
		TreeNode node  = new TreeNode();
		node.setId("0");
		node.setpId("0");
		node.setOpen(1);
		node.setName("全部组织+人员");
		node.setNocheck(true);
		newlistnode.add(node);

		TEmp emp = new TEmp();
		emp.setStatus(1);
		List<TEmp> listemp = empDao.getList(emp);

		List<TreeNode> listtreenode  = orgDao.getListTree();
		for (TreeNode treeNode : listtreenode) {
			List<TEmp> newemplist = getEmpByOrgid(listemp,Integer.parseInt(treeNode.getId()));
			treeNode.setNocheck(true);
			treeNode.setId("orgid"+treeNode.getId());
			if (!treeNode.getpId().equals("0")) {
				treeNode.setpId("orgid"+treeNode.getpId());
			}
			for (TEmp tEmp : newemplist) {
				node = new TreeNode();
				node.setId(String.valueOf(tEmp.getId()));
				node.setName(tEmp.getName());
				node.setpId(treeNode.getId());
				node.setNocheck(false);
				node.setType(2);
				if (StringUtils.hasText(empids)) {
					String[] empidsp = empids.split(",");
					for (String empid : empidsp) {
						if(node.getId().equals(empid)){
							node.setChecked(1);
						}
					}
				}
				newlistnode.add(node);
			}
			newlistnode.add(treeNode);
		}
		return newlistnode;
	
	}


	public String getFzridsByClientid(Integer clientid){
		List<TEmp> list = empDao.getFzridsByClientid(clientid);
		if (list==null) {
			return "";
		}
		String ids = "";
		for (TEmp mo : list) {
			ids += mo.getId()+","; 
		}
		return StringUtils.hasText(ids)?ids.substring(0, ids.length()-1):ids;
	}

	private static List<TEmp> getEmpByOrgid(List<TEmp> listemp,int orgid){
		List<TEmp> newemplist = new ArrayList<>();
		for (TEmp tEmp : listemp) {
			if (tEmp.getOrgid().intValue()==orgid) {
				newemplist.add(tEmp);
			}
		}
		return newemplist;
	}
	
	public Map<String, Object> getClientGroupByEmp(){
		List<Map<String, Object>> list = empDao.getClientGroupByEmp();
		List<Object> listtitle = new ArrayList<>();
		List<Object> listvalue = new ArrayList<>();
		Map<String, Object> recored = new HashMap<>();
		for (Map<String, Object> map : list) {
			listtitle.add(map.get("NAME"));
			listvalue.add(map.get("CLIENTNUM"));
		}
		recored.put("categories", listtitle);
		recored.put("data", listvalue);
		return recored;
	}
	
	public Map<String, Object> getContactGroupByEmp(String contacttimesta,String contacttimeend){
		List<Map<String, Object>> list = empDao.getContactGroupByEmp(contacttimesta,contacttimeend);
		List<Object> listtitle = new ArrayList<>();
		List<Object> listvalue = new ArrayList<>();
		Map<String, Object> recored = new HashMap<>();
		for (Map<String, Object> map : list) {
			listtitle.add(map.get("NAME"));
			listvalue.add(map.get("CONTACTNUM"));
		}
		recored.put("categories", listtitle);
		recored.put("data", listvalue);
		return recored;
	}



}
