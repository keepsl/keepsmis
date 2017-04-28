package com.keeps.tools.utils.velocity;

import org.apache.velocity.tools.generic.DateTool;

import com.keeps.tools.utils.DateUtils;

/** 
 * <p>Title: DateTool.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月28日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class DatesTool extends DateTool{

	public String format(String pattern, String date) {
		return DateUtils.format(date, pattern);
	}

	
}
