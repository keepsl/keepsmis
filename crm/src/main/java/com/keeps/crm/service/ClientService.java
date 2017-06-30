package com.keeps.crm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.service.SoftService;
import com.keeps.model.TClient;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ClientService.java</p>  
 * <p>Description: 客户管理Service接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ClientService extends SoftService{
	
	public Page queryList(TClient client,Integer operType);

	public TClient getById(Integer id);

	public List<TClient> getByIds(String ids);
	
	public String saveOrUpdate(TClient client,Integer operType);
	
	public String updateBatch(TClient client,String clientids,Integer operType);
	
	public String saveEmpClient(String empids, Integer clientid);
	
	public String updateFieidById(String fieid, String value, String ids, Integer operType);
	
	public String saveClientfile(MultipartFile file, HttpServletRequest request,Integer operType);
	
}
