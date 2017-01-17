package com.keeps.webservice.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.keeps.core.dao.SoftDao;
import com.keeps.core.model.listener.RemoveRollback;
import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.core.service.SoftService;
import com.keeps.tools.utils.EditStateEnum;
import com.keeps.tools.utils.EditType;
import com.keeps.webservice.service.TaobaoService;
import com.keeps.webservice.utils.ReturnData;

/** 
 * <p>Title: TaobaoServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
@Repository("taobaoService")
public class TaobaoServiceImpl  extends AbstractService implements TaobaoService {
	
	public ReturnData loadMarkTest(Map<String, String> params) {
		System.out.println("1222");
		return null;
	}
	
}
