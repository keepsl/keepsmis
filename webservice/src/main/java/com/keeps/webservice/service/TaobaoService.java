package com.keeps.webservice.service;

import java.util.Map;

import com.keeps.core.service.SoftService;
import com.keeps.webservice.utils.ReturnData;

/** 
 * <p>Title: TaobaoService.java</p>  
 * <p>Description: 淘宝数据处理 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface TaobaoService extends SoftService{

	public ReturnData loadMarkTest(Map<String, String> params);
}
