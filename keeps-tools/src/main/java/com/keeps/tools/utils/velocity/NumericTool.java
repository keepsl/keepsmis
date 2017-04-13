package com.keeps.tools.utils.velocity;

import org.apache.velocity.tools.generic.NumberTool;

/**
 * <p>
 * Title: NumericTool.java
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
 * @date 创建日期：2017年4月8日 修改日期： 修改人： 复审人：
 */
public class NumericTool extends NumberTool {

	public String getTheDecimal(Object o) {
		if (o == null) {
			return "0";
		}
		String str = String.valueOf(o);
		return ((str.indexOf(".")) != -1) ? str.substring(str.indexOf(".") + 1) : "0";
	}

	public static String subZeroAndDot(Object o) {
		if (o == null) {
			return "0";
		}
		String str = String.valueOf(o);
		if (str.indexOf(".") > 0) {
			str = str.replaceAll("0+?$", "");// 去掉多余的0
			str = str.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return str;
	}
}
