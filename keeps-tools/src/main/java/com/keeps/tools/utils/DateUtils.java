package com.keeps.tools.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class DateUtils {
	public static final String YEAR = "年";
	public static final String MONTH = "月";
	public static final String DAY = "日";
	public static final String SEASON = "季";
	public static final int SEASON_TYPE_2 = 2;
	public static final int ONE_SECOND = 1000;
	public static final int ONE_MINUTE = 60000;
	public static final int ONE_HOUR = 3600000;
	public static final long ONE_DAY = 86400000L;
	public static final long ONE_WEEK = 604800000L;
	public static final String DEFALUT_DATE_PATTERN = "yyyy-MM-dd";
	public static final String DEFALUT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFALUT_DATETIME_PATTERN_FORMAT_TWO = "yyyy-MM-dd-HH-mm-ss";
	public static final String DEFALUT_DATETIME_PATTERN_ONE = "yyyyMMdd";
	public static final String DEFALUT_DATETIME_PATTERN_THREE = "yyyyMMddHHmmss";

	public static Date getNow() {
		return new Date();
	}

	public static Date getNow(String pattern) {
		return parse(format(getNow(), pattern), pattern);
	}

	public static String formatNow(String pattern) {
		return format(getNow(), pattern);
	}

	public static String formatNow() {
		return formatNow("yyyy-MM-dd HH:mm:ss");
	}

	public static String format(String date, String pattern) {
		Date d = parse(date);

		return format(d, pattern);
	}

	public static int getNowYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(1);
	}

	public static String getNowYearFirstDay() {
		return getNowYear() + "-01-01";
	}

	public static int getNowMonth() {
		Calendar cal = Calendar.getInstance();
		return (cal.get(2) + 1);
	}

	public static String getNowMonthSeason(int type) {
		String season = "";
		if (2 == type) {
			int m = getNowMonth();
			if ((m >= 2) && (m <= 7))
				season = "1";
			else {
				season = "2";
			}
		}
		return season;
	}

	public static String getNowTimeNoon() {
		Calendar c = Calendar.getInstance();

		int n = c.get(11);

		if (n < 12) {
			return "1";
		}
		return "2";
	}

	public static int getNowSeason() {
		return (getNowMonth() / 4 + 1);
	}

	public static int getNowDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(5);
	}

	public static Date getBeginOfToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static Date getEndOfToday() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		cal.set(14, 999);
		return cal.getTime();
	}

	public static Date getBeginOfDay(Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		Date ds = new Date(cal.getTimeInMillis());
		return ds;
	}

	public static Date getEndOfDay(Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		cal.set(14, 999);
		Date ds = new Date(cal.getTimeInMillis());
		return ds;
	}

	public static Date getNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(5, cal.get(5) + 1);
		return cal.getTime();
	}

	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginOfToday());
		cal.set(5, cal.get(5) - 1);
		return cal.getTime();
	}

	public static Date getXDayByNow(int x) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getEndOfToday());
		cal.set(5, cal.get(5) + x);
		return cal.getTime();
	}

	public static Date getTomorrow() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginOfToday());
		cal.set(5, cal.get(5) + 1);
		return cal.getTime();
	}

	public static int getYearOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(1);
	}

	public static int getMonthOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(2);
	}

	public static Date getFirstDayOfCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(5, cal.getActualMinimum(5));
		return getBeginOfDay(cal.getTime());
	}

	public static Date getEndDayOfCurMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(5, cal.getActualMaximum(5));
		return getBeginOfDay(cal.getTime());
	}

	public static Date getMax(Date date1, Date date2) {
		if (date1 == null)
			return date2;
		if (date2 == null)
			return date1;
		if (date1.after(date2))
			return date1;
		return date2;
	}

	public static Date getMin(Date date1, Date date2) {
		if (date1 == null)
			return date2;
		if (date2 == null)
			return date1;
		if (date1.after(date2))
			return date2;
		return date1;
	}

	public static int getWeekOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int xq = cal.get(7) - 1;
		return ((xq == 0) ? 7 : xq);
	}

	public static boolean compareFirstMax(String first, String second) {
		if (StringUtils.isBlank(first)) {
			return false;
		}
		if (StringUtils.isBlank(second)) {
			return true;
		}
		Date d1 = parse(first);
		Date d2 = parse(second);
		return (!(d1.after(d2)));
	}

	public static boolean after(String first, String second) {
		Date d1 = parse(first);
		Date d2 = parse(second);
		return d1.after(d2);
	}

	public static int daysOfMonth(int year, int month) {
		Assert.isTrue((month >= 0) && (month <= 12), "月份[" + month + "]错误!");
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			if (isLeap(year))
				return 29;
			return 28;
		}
		return 0;
	}

	public static boolean isLeap(int year) {
		boolean divBy4 = year % 4 == 0;
		boolean divBy100 = year % 100 == 0;
		boolean divBy400 = year % 400 == 0;
		return ((divBy4) && (((!(divBy100)) || (divBy400))));
	}

	public static boolean isDate(String date) {
		try {
			parse(date);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static Date addYear(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(1);
		calendar.set(1, i + year);
		return calendar.getTime();
	}

	public static Date addMonth(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(2);
		month += i;
		int deltaY = month / 12;
		month %= 12;
		calendar.set(2, month);
		if (deltaY != 0) {
			int year = calendar.get(1);
			calendar.set(1, deltaY + year);
		}
		return calendar.getTime();
	}

	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(5, day);
		Date d = calendar.getTime();
		return d;
	}

	public static Date addHour(Date date, long l) {
		long oldD = date.getTime();
		long deltaD = l * 60L * 60L * 1000L;
		long newD = oldD + deltaD;
		Date d = new Date(newD);
		return d;
	}

	public static Date addMinute(Date date, long l) {
		long oldD = date.getTime();
		long deltaD = l * 60L * 1000L;
		long newD = oldD + deltaD;
		Date d = new Date(newD);
		return d;
	}

	public static Date addSecond(Date date, long l) {
		long oldD = date.getTime();
		long deltaD = l * 1000L;
		long newD = oldD + deltaD;
		Date d = new Date(newD);
		return d;
	}

	public static Date addMilliSecond(Date date, long l) {
		long oldD = date.getTime();
		long deltaD = l;
		long newD = oldD + deltaD;
		Date d = new Date(newD);
		return d;
	}

	public static Date addNowHourMinSec(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		Calendar now = Calendar.getInstance();

		calendar.set(11, now.get(11));
		calendar.set(12, now.get(12));
		calendar.set(13, now.get(13));

		return new Date(calendar.getTimeInMillis());
	}

	public static String format(Date aDate) {
		return format(aDate, "yyyy-MM-dd HH:mm:ss");
	}

	public static String format(Date aDate, String pattern) {
		if (aDate == null)
			return "";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(aDate);
	}

	public static Date parse(String strDate) {
		return parse(strDate, new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
	}

	public static Date parse(String strDate, String aMask) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		try {
			date = df.parse(strDate);
		} catch (Exception e) {
			return null;
		}

		return date;
	}

	public static String formatTimestamp(String str, String aMask) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		Date d = new Timestamp(Long.valueOf(str).longValue());
		SimpleDateFormat df = new SimpleDateFormat(aMask);
		return df.format(d);
	}

	public static String formatTimestamp(String str) {
		return formatTimestamp(str, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date parseTimestamp(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return new Timestamp(Long.valueOf(str).longValue());
	}

	public static Date parseAddBeginOfDay(String date) {
		return parse(date + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
	}

	public static Date parseAddEndOfDay(String date) {
		return parse(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
	}

	public static Date parse(String strDate, String[] aMasks) {
		if (StringUtils.isBlank(strDate))
			return null;
		for (String aMask : aMasks) {
			Date date = parse(strDate, aMask);
			if (date != null) {
				return date;
			}
		}
		return null;
	}

	public static String getCurrentZhDate() {
		StringBuffer sb = new StringBuffer();
		sb.append(getNowYear());
		sb.append("年");
		sb.append(getNowMonth());
		sb.append("月");
		sb.append(getNowDayOfMonth());
		sb.append("日");

		return sb.toString();
	}

	public static long dateDiff(DateIntervalType interval, Date dDate1, Date dDate2) {
		int desiredField = 0;
		int coef = 1;
		Date date1;
		Date date2;
		if (dDate1.getTime() > dDate2.getTime()) {
			coef = -1;
			date1 = dDate2;
			date2 = dDate1;
		} else {
			date1 = dDate1;
			date2 = dDate2;
		}

		int field;
		if (interval.ordinal() == DateIntervalType.YEAR.ordinal()) {
			field = 1;
		} else if (interval.ordinal() == DateIntervalType.MONTH.ordinal()) {
			field = 2;
		} else if (interval.ordinal() == DateIntervalType.DAY.ordinal()) {
			field = 5;
		} else if (interval.ordinal() == DateIntervalType.WEEK.ordinal()) {
			field = 4;
		} else if (interval.ordinal() == DateIntervalType.HOUR.ordinal()) {
			field = 5;
			desiredField = 11;
		} else if (interval.ordinal() == DateIntervalType.MINUTE.ordinal()) {
			field = 5;
			desiredField = 12;
		} else if (interval.ordinal() == DateIntervalType.SECOND.ordinal()) {
			field = 5;
			desiredField = 13;
		} else {
			throw new IllegalArgumentException("unkown interval!");
		}
		Calendar calTmp = Calendar.getInstance();
		calTmp.setTime(date1);
		long nbOccurence = 0L;
		calTmp.add(field, 1);
		Date dateTemp = calTmp.getTime();
		while (dateTemp.getTime() <= date2.getTime()) {
			calTmp.add(field, 1);
			dateTemp = calTmp.getTime();
			nbOccurence += 1L;
		}
		if ((desiredField == 11) || (desiredField == 12) || (desiredField == 13)) {
			calTmp.setTime(date1);
			calTmp.add(field, (int) nbOccurence);
			dateTemp = calTmp.getTime();
			switch (desiredField) {
			case 11:
				nbOccurence *= 24L;
				break;
			case 12:
				nbOccurence = nbOccurence * 24L * 60L;
				break;
			case 13:
				nbOccurence = nbOccurence * 24L * 60L * 60L;
			}

			calTmp.add(desiredField, 1);
			dateTemp = calTmp.getTime();
			while (dateTemp.getTime() <= date2.getTime()) {
				calTmp.add(desiredField, 1);
				dateTemp = calTmp.getTime();
				nbOccurence += 1L;
			}
		}
		return (nbOccurence * coef);
	}

	public static void main(String[] args) throws Exception {
	}

	private static void testMain() throws Exception {
		System.out.println(getNowMonth());
	}

	public static enum DateIntervalType {
		YEAR, MONTH, WEEK, DAY, HOUR, MINUTE, SECOND;
	}
}
