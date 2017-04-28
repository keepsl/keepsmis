package com.keeps.tools.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


/** 
 * <p>Title: CommonUtils.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class CommonUtils {
	public static void main(String[] args) throws IOException {
		System.out.println(computePercent(0,10));
		/*List<String> dates = getDateRange("2016/01/12","2016/01/15");
		for (String string : dates) {
			System.out.println(string);
		}*/
		//System.out.println(isWinterm("2016-5-2","10-2","5-2"));
		DecimalFormat   fnum   =   new   DecimalFormat("##0.00");  
    	String path = "C:";
		File diskPartition = new File(path.substring(0,path.indexOf(":")+1));
        float usablePatitionSpace = diskPartition.getUsableSpace();
		System.out.println (CommonUtils.subZeroAndDot(fnum.format(usablePatitionSpace / (1024*1024*1024))));
		
		getSubStr("1,2", 5, ",");
	}

	public static String getSubStr(String str, int num, String regex) {
		String result = "";
		int i = 0;
		if (CommonUtils.isNull(str)) {
			return "";
		}
		if (str.split(regex).length<=num) {
			return str;
		}
		while (i < num) {
			int lastFirst = str.lastIndexOf(regex);
			result = str.substring(lastFirst) + result;
			str = str.substring(0, lastFirst);
			i++;
		}
		System.out.println(result.substring(1));
		return result.substring(1);
	}
	/**
	  * @Title:			isWinterm 
	  * @Description:	判断是否冬令时
	  * @param:
	  * @return: 
	  * @author:		李园园
	  * @data:			2016年9月27日
	 */
	public static boolean isWinterm(String coursedate,String winterStartDate, String summerStartDate){
		String[] wintermd =  winterStartDate.split("-");
		int winterm = Integer.parseInt(wintermd[0]);
		int winterd = Integer.parseInt(wintermd[1]);

		String[] summermd =  summerStartDate.split("-");
		int summerm = Integer.parseInt(summermd[0]);
		int summerd = Integer.parseInt(summermd[1]);

		String[] dq = coursedate.split("-");
		int dqm = Integer.parseInt(dq[1]);
		int dqd = Integer.parseInt(dq[2]);
		if ((dqm>=winterm && dqd>=winterd) || (dqm<=summerm && dqd<summerd)) {//是冬令时
			return true;
		}
		return false;
	}

	/**
	  * @Title:			getWeekOfDate 
	  * @Description:	根据日期获得周几
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月7日
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
	/**
	  * @Title:			getWeekIndexOfDate 
	  * @Description:	根据日期获得周几
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月7日
	 */
	public static Integer getWeekIndexOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK)-1;
		if (w <= 0)
			w = 7;
		return w;
	}
	
	/**
	  * @Title:			dateToWeek 
	  * @Description:	获得一周的所有日期
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月7日
	 */
	public static List<Date> getDateToWeek(Date dt) {  
	    int b = dt.getDay();  
	    Date fdate;  
	    List<Date> list = new ArrayList<Date>();  
	    Long fTime = dt.getTime() - b * 24 * 3600000;  
	    for (int a = 1; a <= 7; a++) {  
	        fdate = new Date();  
	        fdate.setTime(fTime + (a * 24 * 3600000));  
	        list.add(a-1, fdate);  
	    }  
	    return list;  
	}
	/**
	  * @Title:			getNextWeekOfDate 
	  * @Description:	获得往后周的某一天的日期
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月7日
	 */
	public static Date getNextWeekOfDate(Date dt,Integer day,Integer w){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(Calendar.WEEK_OF_YEAR, day);
		cal.set(Calendar.DAY_OF_WEEK, w);
		return cal.getTime();
	}
	
	/**
	  * @Title:			getDateAfterWeek 
	  * @Description:	指定日期在规定的日期后是第几周
	  * @param:
	  * @return: 
	  * @author:		李园园
	  * @data:			2016年9月29日
	 */
	public static int getDateAfterWeek(Date begindate,Date enddate){
		Calendar cbegin = Calendar.getInstance();  
		Calendar cend = Calendar.getInstance();  
		cbegin.setTime(begindate);
		cend.setTime(enddate);
		int count = 1;
		while(!cbegin.after(cend)){
			int w = cbegin.get(Calendar.DAY_OF_WEEK);
			if (w <= 0)
				w = 7;
			if(w==Calendar.SUNDAY){//周一
				count++;
			}
			//System.out.println("第"+count+"周  日期："+new java.sql.Date(cbegin.getTime().getTime())+","+w);
			cbegin.add(Calendar.DAY_OF_YEAR, 1);
		}
	    return count;
	}
	/**
	  * @Title:			getIp 
	  * @Description:	获得IP
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月6日
	 */
	public static String getIp(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarded-For"); // for weblogic
		if (ip == null || ip.length() == 0) {
			ip = request.getHeader("WL-Proxy-Client-IP");// for weblogic
		}
		if (ip == null || ip.length() == 0) {
			ip = request.getRemoteAddr(); // for others
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "127.0.0.1";
		}
		return ip;
	}
	/**
	  * @Title:			hexToInt 
	  * @Description:	16进制转换10进制
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月3日
	 */
	public static Long hexToLong(String strHex){
		if (IsHex(strHex)) {
			return Long.parseLong(strHex,16);
		}
		return null;
	}
	
	//判断是否是16进制数  
	public static boolean IsHex(String strHex){  
		int i = 0;  
		if ( strHex.length() > 2 ){
			if ( strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x') ){  
				i = 2;
			}
		}  
		for ( ; i<strHex.length(); ++i ){  
			char ch = strHex.charAt(i);  
			if ( (ch>='0' && ch<='9') || (ch>='A' && ch<='F') || (ch>='a' && ch<='f') )  
				continue;  
			return false;  
		}  
		return true;  
	}  
	
	/**
	  * @Title:			countTime 
	  * @Description:	传入一个时间计算分钟
	  * @param:
	  * @return: 
	  * @author:		李园园
	  * @data:			2016年10月11日
	 */
	public static Integer toTime(String time){
		String[] times = time.split(":");
		Integer m = Integer.parseInt(times[0])*60+Integer.parseInt(times[1]);
		return m;
	}
	
	/**
	  * @Title:			sunTime 
	  * @Description:	时间与分钟相加函数
	  * @param:			Time:HH:MM  m:M
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年8月24日
	 */
	public static String sunTime(String Time,Integer m){
		String[] times = Time.split(":");
		int mm = (Integer.parseInt(times[0])*60+Integer.parseInt(times[1]))+m;
		String hm = mm/60+":"+((mm%60)<10?"0"+(mm%60):(mm%60));
		return hm;
	}
	/**
	  * @Title:			minusTime 
	  * @Description:	时间与分钟相减函数
	  * @param:			Time:HH:MM  m:M
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月14日
	 */
	public static String minusTime(String Time,Integer m){
		String[] times = Time.split(":");
		int mm = (Integer.parseInt(times[0])*60+Integer.parseInt(times[1]))-m;
		String hm = mm/60+":"+((mm%60)<10?"0"+(mm%60):(mm%60));
		return hm;
	}
	
	/**
	  * @Title:			isNumeric 
	  * @Description:	验证是否为数字
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年8月26日
	 */
	public static boolean isNumeric(String str){
	   return Pattern.matches("[0-9]*", str);
	}
	
	/**
	  * @Title:			isExistStr 
	  * @Description:   验证一段字符串中是否存在字符
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年8月26日
	 */
	public static boolean isExistStr(String str,String[] strs){
		for (String string : strs) {
			if (str.equals(string)) {
				return true;
			}
		}
		return false; 
	}
	public static boolean isExistStr(Integer str,Integer[] strs){
		for (Integer integer : strs) {
			if (str==integer) {
				return true;
			}
		}
		return false; 
	}
	
	/**
	  * @Title:			isNotNull 
	  * @Description:	判断是否不为空
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年8月24日
	 */
	public static boolean isNotNull(Object o){
		return !isNull(o);
	}
	/**
	  * @Title:			isNull 
	  * @Description:	判断是否为空
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年8月24日
	 */
	public static boolean isNull(Object o){
		if (o==null) {
			return true;
		}
		if (!"".equals(o.toString().trim())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	  * @Title:			toBigDecimal 
	  * @Description:	转换BigDecimal 
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年8月27日
	 */
	public static BigDecimal toBigDecimal(Object o) {
        if (o == null || o.toString().equals("") || o.toString().equals("NaN")) {
            return new BigDecimal(0);
        }
        return new BigDecimal(o.toString());
    }
    /**
      * @Title:			getPercent 
      * @Description:	计算两数百分比
      * @param:
      * @return: 
      * @author:		keeps
      * @data:			2016年8月27日
     */
    public static String computePercent(Object divisor, Object dividend){
        if(isNotNull(divisor) && isNotNull(dividend)){
        	if("0".equals(String.valueOf(divisor)) || "0".equals(String.valueOf(dividend))){
        		return "0%";
            }
        	NumberFormat percent = NumberFormat.getPercentInstance();   
            percent.setMaximumFractionDigits(0);
            BigDecimal a = toBigDecimal(divisor);
            BigDecimal b = toBigDecimal(dividend);
//            System.out.println(divisor);
//            System.out.println(dividend);
            return percent.format(a.divide(b, 4, BigDecimal.ROUND_DOWN));
        }
        return "0%";
    }
    
    /**
      * @Title:			subZeroAndDot 
      * @Description:	使用正则去掉小数点后对于的.和0
      * @param:
      * @return: 
      * @author:		李园园
      * @data:			2016年12月28日
     */
    public static String subZeroAndDot(String s){    
        if(s.indexOf(".") > 0){    
            s = s.replaceAll("0+?$", "");//去掉多余的0    
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉    
        }    
        return s;    
    }    
    public static String computePercent(Integer divisor, Integer dividend){
    	if (dividend==0) {
    		return "0%";
		}
    	double percent = divisor.doubleValue() / dividend.doubleValue();
		DecimalFormat df = new DecimalFormat("0.00%");
    	String s = df.format(percent);
    	s = s.substring(0, s.length()-1);
    	if(s.indexOf(".") > 0){
    		s = s.replaceAll("0+?$", "");//去掉后面无用的零
    		s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
    	}
    	return s+"%";
    }
    /**
      * @Title:			computeProportion 
      * @Description:	计算比例
      * @param:
      * @return: 
      * @author:		keeps
      * @data:			2016年8月27日
     */
    public static String computeProportion(Object divisor, Object dividend){
    	if(isNotNull(divisor) && isNotNull(dividend)){
    		BigDecimal a = toBigDecimal(divisor);
            BigDecimal b = toBigDecimal(dividend);
            return a.divide(b, 2, BigDecimal.ROUND_DOWN).toString();
    	}
    	return "";
    }
    
}
