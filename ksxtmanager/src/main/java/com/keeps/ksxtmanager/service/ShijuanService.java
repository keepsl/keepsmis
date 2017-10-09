package com.keeps.ksxtmanager.service;

import com.keeps.core.service.SoftService;
import com.keeps.ksxtmanager.model.KsShijuan;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ShijuanService.java</p>  
 * <p>Description: 试卷管理Service接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年10月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface ShijuanService extends SoftService{

	public Page queryList(KsShijuan shijuan);
	
	public Page querywksjList(KsShijuan shijuan);
	
	public String saveOrUpdate(KsShijuan shijuan);
	
	public String delete (String ids);
	
	public KsShijuan getById(Integer id);
	
}
