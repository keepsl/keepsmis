package com.keeps.manage.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.service.SoftService;
import com.keeps.model.TAdvPosition;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: AdvPositionService.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface AdvPositionService extends SoftService{
	
	public Page queryList(TAdvPosition advPosition);
	
	public List<TAdvPosition> queryAll();

	public TAdvPosition getById(Integer id);
	
	public String saveOrUpdate(TAdvPosition advPosition, MultipartFile defaultcontentfile, HttpServletRequest request);
	
	public String updateFieidById(String fieid,Integer value,String ids);

	public String delete(String ids);
	
	public String cacheAdvFile(String ids);
	
}
