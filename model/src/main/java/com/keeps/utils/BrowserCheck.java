package com.keeps.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * <p>Title: BrowserCheck.java</p>  
 * <p>Description: 浏览器类型判断 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月7日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class BrowserCheck {
	public static String encodeText(String userAgent,String name) throws UnsupportedEncodingException{
		userAgent=userAgent.toUpperCase();
		if(userAgent.contains("CHROME")){//chrome
			name = new String(name.getBytes("UTF-8"), "ISO8859-1");
		}else if(userAgent.contains("FIREFOX")){//firefox
			name = new String(name.getBytes("UTF-8"), "ISO8859-1");
		}else if(userAgent.contains("rv:11.0")&&userAgent.contains("GECKO")){//ie11
			name = URLEncoder.encode(name,"UTF-8");
		}else if(userAgent.contains("MSIE")){//ie11以下
			name = URLEncoder.encode(name,"UTF-8");
		}else{
			name = URLEncoder.encode(name,"UTF-8");
		}
		return name;
	}
}
