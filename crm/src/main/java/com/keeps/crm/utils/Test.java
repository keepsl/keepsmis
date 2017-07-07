package com.keeps.crm.utils;

import com.keeps.tools.utils.DateUtils;

import net.sourceforge.pinyin4j.PinyinHelper;

/** 
 * <p>Title: Test.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class Test {

	public static void main(String[] args) {

		// 中英文混合的一段文字

		String str = "单";

		String strPinyin = Hanyu.getFullSpell(str);

		System.out.println(strPinyin);
		
		System.out.println(Hanyu.getFirstSpell(str));

		System.out.println(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -7)));
		System.out.println(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -14)));
		

		System.out.println(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -15)));
		System.out.println(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -29)));
		
		System.out.println(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -100)));

	}

}
