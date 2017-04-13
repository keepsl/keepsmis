package com.keeps.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.keeps.tools.utils.StringUtils;  
/** 
 * <p>Title: ValidateUtil.java</p>  
 * <p>Description: 保存数据验证工具类</p>  
 * <p>Copyright: Copyright (c) 新开普电子股份有限公司 2016</p>  
 * @author 李园园
 * @version
 * @date 创建日期：2016年10月14日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class ValidateUtil {

	/**
	 * 正则表达式：特殊字符
	 */
    public static final String REGEX_SPECIAL = "[`@$%^&*=|{}<>@￥%……&*{}\\\\]";
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";
 
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0-1,5-9]))\\d{8}$";
 
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
 
    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
 
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
 
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$";

    /**
     * 正则表达式：验证MAC地址
     */
    public static final String REGEX_MAC = "^[A-F0-9]{2}(-[A-F0-9]{2}){5}$";
	/**
	  * @Title:			islength 
	  * @Description:	str要验证的字符串，len最大长度
	  * @param:
	  * @return: 
	  * @author:		李园园
	  * @data:			2016年10月14日
	 */
	public static boolean islength(String str,int len){
		if (StringUtils.notText(str)) {
			return true;
		}
		if (str.length()>len) {
			return false;
		}
		return true;
	}
	
    /**
     * 校验用户名
     * 
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }
 
    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
 
    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
 
    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
 
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
 
    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }
 
    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
 
    /**
     * 校验特殊字符
     * 
     * @param isMacAddr
     * @return
     */
    public static boolean isMacAddr(String mac) {
   		mac = mac.replace(':', '-');  // 替换字符串
        return Pattern.matches(REGEX_MAC, mac);
    }
 
    /**
     * 校验特殊字符
     * 
     * @param isSpecial
     * @return
     */
    public static boolean isSpecial(String str) {
        Pattern p = Pattern.compile(REGEX_SPECIAL);
        Matcher m = p.matcher(str);
        return !m.find();
    }
    public static void main(String[] args) {
        System.out.println(isMacAddr("C8:5B:76:17:7F:DD"));
    }
}
