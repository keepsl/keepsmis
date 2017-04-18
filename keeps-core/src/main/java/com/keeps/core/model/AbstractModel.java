package com.keeps.core.model;

import com.keeps.core.model.AbstractSoftModelEntity;
import com.keeps.core.model.utils.ModelUtil;

/** 
 * <p>Title: AbstractModel.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public abstract class AbstractModel extends AbstractSoftModelEntity<String> {
	public void idhandle() {
		ModelUtil.randomUUID(this);
	}
}
