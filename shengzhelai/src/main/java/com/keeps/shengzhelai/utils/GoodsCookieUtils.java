package com.keeps.shengzhelai.utils;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keeps.tools.utils.CookieUtils;
import com.keeps.tools.utils.StringUtils;

/** 
 * <p>Title: CookieGoodsUtils.java</p>  
 * <p>Description: 商品缓存管理工具类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月18日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class GoodsCookieUtils {
	private static final String GOODS_COOKIE_VIEW_NAME = "keeps_goods_view_aabbccddeeff";
	private static final Integer GOODS_COOKIE_TIME = 168*3600;

	public static String getGoodsids(HttpServletRequest request){
		//获得cookie中的商品ID
		String cookievalue = CookieUtils.getCookieValue(request, GOODS_COOKIE_VIEW_NAME);
		return cookievalue;
	}
	
	public static void setGoodsids(HttpServletRequest request,HttpServletResponse response,String id){
		String cookievalue = GoodsCookieUtils.getGoodsids(request);
		if (StringUtils.notText(cookievalue)) {
			CookieUtils.setCookie(response, GOODS_COOKIE_VIEW_NAME, id.toString(), GOODS_COOKIE_TIME);
		}else{
			cookievalue += ","+id.toString();
			String[] valuearr = cookievalue.split(",");
			Set<String> setvalue = new HashSet<>();
			//去除重复
			for (String v : valuearr) {
				setvalue.add(v.trim());
			}
			//超过50，删除第一个
			if (setvalue.size()>50) {
				for (String ss : setvalue) {
					setvalue.remove(ss);
					break;
				}
			}
			cookievalue = setvalue.toString();
			cookievalue = cookievalue.substring(1, cookievalue.length()-1).replaceAll(" ", "");
			CookieUtils.setCookie(response, GOODS_COOKIE_VIEW_NAME, cookievalue, GOODS_COOKIE_TIME);
		}
	}

	
}
