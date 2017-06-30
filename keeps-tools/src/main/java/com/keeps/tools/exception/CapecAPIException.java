package com.keeps.tools.exception;

import com.keeps.tools.common.SoftException;
import com.keeps.tools.common.SoftUtils;

/**
 * <p>
 * Title: CapecAPIException.java
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
public class CapecAPIException extends RuntimeException implements SoftException, SoftUtils {
	public CapecAPIException(Integer code) {
		super(String.valueOf(code));
	}
}
