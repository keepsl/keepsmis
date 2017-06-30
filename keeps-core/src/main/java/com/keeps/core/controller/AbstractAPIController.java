package com.keeps.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keeps.tools.exception.CapecAPIException;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.APIModel;

/**
 * <p>
 * Title: AbstractAPIController.java
 * </p>
 * <p>
 * Description: @TODO
 * </p>
 * <p>
 * Copyright: Copyright (c) KEEPS
 * </p>
 * 
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月25日 修改日期： 修改人： 复审人：
 */
public class AbstractAPIController extends AbstractController {
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public Map exceptionHandler(Exception ex) {
		Map model = new HashMap();
		if (ex instanceof CapecException) {
			model.put(APIModel.CODE, APIModel.SYSTEM_ERROR);
			model.put(APIModel.MSG, ex.getMessage());
		} else if (ex instanceof CapecAPIException) {
			model.put(APIModel.CODE, ex.getMessage());
		} else {
			model.put(APIModel.CODE, APIModel.SYSTEM_ERROR);
			ex.printStackTrace();
			this.log.error("系统出现未知异常:" + ex.getMessage());
		}
		return model;
	}

	public Map success() {
		Map map = new HashMap();
		map.put(APIModel.CODE, APIModel.SYSTEM_SUCCESS);
		return map;
	}
}
