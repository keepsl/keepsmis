package com.keeps.tools.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.Assert;

public class NumberUtils {

	public static final String S0C4_FORMAT = "#0.0000";
	public static final String S3C4_FORMAT = "#,##0.0000";
	public static final String S0C0_FORMAT = "#0";
	public static final String S3C0_FORMAT = "#,##0";
	public static final String SXCX4_FORMAT = "0.####";
	public static final int N_1000 = 1000;
	public static final int N_60 = 60;
	public static final int N_24 = 24;
	public static final int N_N1 = -1;

	public static String format(Object obj, String pattern) {
		if ((obj == null) || (!(canParseDouble(obj))))
			return "";
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(toDouble(obj));
	}

	public static String defaultFormat(double dbl) {
		return format(Double.valueOf(dbl), "#,##0.0000");
	}

	public static String defaultFormat(int dbl) {
		return format(Integer.valueOf(dbl), "#,##0");
	}

	public static double defaultFormatSOC4(double dbl) {
		return toDouble(format(Double.valueOf(dbl), "#0.0000")).doubleValue();
	}

	public static String nullSafeFormat(Object obj, String pattern) {
		if (obj == null)
			return "";
		return format(obj, pattern);
	}

	public static String format(Object obj, int comma, int decimalNum) {
		if (obj == null)
			return "";

		StringBuilder pattern = new StringBuilder();
		int i;
		if (decimalNum >= 0) {
			pattern.append('0');
			for (i = 0; i < decimalNum; ++i) {
				if (pattern.length() == 1) {
					pattern.append('.');
				}
				pattern.append('0');
			}
		}
		if (comma > 0) {
			pattern.insert(0, "#,");
			for (i = 0; i < comma - 1; ++i)
				pattern.insert(2, '#');
		}

		return format(obj.toString(), pattern.toString());
	}

	public static String formatIntByLengthIfNeed(int int4format, int needLength) {
		Assert.isTrue(needLength > 0);

		int realLength = Integer.toString(int4format).length();

		if (realLength > needLength)
			return Integer.toString(int4format);

		StringBuilder ret = new StringBuilder(30);
		for (int i = 0; i < needLength - realLength; ++i) {
			ret.append('0');
		}
		ret.append(int4format);
		return ret.toString();
	}

	public static Double toDouble(Object obj) {
		return toDouble(obj, false);
	}

	public static Double toDoubleNull2Zero(Object obj) {
		Double d = toDouble(obj, false);
		return Double.valueOf((d == null) ? 0.0D : d.doubleValue());
	}

	public static Float toFloat(Object obj) {
		Float f = toFloat(obj, false);
		return Float.valueOf((f == null) ? 0.0F : f.floatValue());
	}

	public static Float toFloat(Object obj, boolean isThrowException) {
		Float d = null;
		try {
			if (obj == null) {
				return Float.valueOf(0.0F);
			}
			String str = obj.toString();
			str = str.replaceAll(",|%|#", "");
			d = new Float(str);
		} catch (Exception e) {
			if (isThrowException) {
				throw new IllegalArgumentException("[" + ObjectUtils.nullSafeToString(obj) + "]转换Float错误!", e);
			}
		}
		return d;
	}

	public static Double toDouble(Object obj, boolean isThrowException) {
		Double d = null;
		try {
			if (obj == null) {
				return Double.valueOf(0.0D);
			}
			String str = obj.toString();
			str = str.replaceAll(",|%|#", "");
			d = new Double(str);
		} catch (Exception e) {
			if (isThrowException) {
				throw new IllegalArgumentException("[" + ObjectUtils.nullSafeToString(obj) + "]转换Double错误!", e);
			}
		}
		return d;
	}

	public static Double toDouble(Object obj, Double defaultValue) {
		Double d = null;
		try {
			String str = obj.toString();
			str = StringUtils.replace(str, ",", "");
			d = new Double(str);
		} catch (Exception e) {
			d = defaultValue;
		}
		return d;
	}

	public static Integer toInteger(Object obj) {
		return toInteger(obj, false);
	}

	public static Integer toInteger(Object obj, boolean isThrowException) {
		Integer i;
		try {
			if (obj == null)
				return Integer.valueOf(0);
			String str = obj.toString();
			str = StringUtils.replace(str, ",", "");
			i = new Integer(str);
		} catch (Exception e) {
			if (isThrowException) {
				throw new IllegalArgumentException("[" + ObjectUtils.nullSafeToString(obj) + "]转换Integer错误!", e);
			}
			i = null;
		}

		return i;
	}

	public static Integer toInteger(Object obj, Integer defaultValue) {
		Integer re;
		try {
			String str = obj.toString();
			str = StringUtils.replace(str, ",", "");
			re = new Integer(str);
		} catch (Exception e) {
			re = defaultValue;
		}
		return re;
	}

	public static Long toLong(Object obj) {
		return toLong(obj, false);
	}

	public static Long toLong(Object obj, boolean isThrowException) {
		Long l = null;
		try {
			String str = obj.toString();
			str = StringUtils.replace(str, ",", "");
			str = StringUtils.replace(str, "'", "");
			l = new Long(str);
		} catch (Exception e) {
			if (isThrowException)
				throw new IllegalArgumentException("[" + ObjectUtils.nullSafeToString(obj) + "]转换Long错误!", e);
		}
		return l;
	}

	public static Long toLong(Object obj, Long defaultValue) {
		Long l;
		try {
			String str = obj.toString();
			str = StringUtils.replace(str, ",", "");
			l = new Long(str);
		} catch (Exception e) {
			l = defaultValue;
		}
		return l;
	}

	public static Short toShort(Object obj) {
		return toShort(obj, false);
	}

	public static Short toShort(Object obj, boolean isThrowException) {
		Short s = null;
		try {
			String str = obj.toString();
			str = StringUtils.replace(str, ",", "");
			s = new Short(str);
		} catch (Exception e) {
			if (isThrowException)
				throw new IllegalArgumentException("[" + ObjectUtils.nullSafeToString(obj) + "]转换Short错误!", e);
		}
		return s;
	}

	public static int intValue(Object obj) {
		return toInteger(obj, true).intValue();
	}

	public static int intValue(Object obj, int defaultValue) {
		return ((toInteger(obj, false) == null) ? defaultValue : toInteger(obj, false).intValue());
	}

	public static long longValue(String str) {
		return Long.parseLong(str);
	}

	public static long longValue(String str, long defaultValue) {
		return ((toLong(str, false) == null) ? defaultValue : toLong(str).longValue());
	}

	public static double doubleValue(Object obj) {
		return toDouble(obj, true).doubleValue();
	}

	public static double doubleValue(Object obj, double defaultValue) {
		Double d = toDouble(obj, false);
		return ((d == null) ? defaultValue : d.doubleValue());
	}

	public static Long[] toLongArray(Object[] objs) {
		if ((objs == null) || (objs.length == 0)) {
			return new Long[0];
		}
		List result = new ArrayList();
		for (Object o : objs) {
			result.add(toLong(o.toString(), true));
		}
		return ((Long[]) result.toArray(new Long[0]));
	}

	public static int[] intArray(Integer[] array) {
		int[] result = new int[array.length];
		int i = 0;
		for (Integer value : array) {
			result[(i++)] = value.intValue();
		}
		return result;
	}

	public static int[] intArray(List<Integer> list) {
		int[] result = new int[list.size()];
		int i = 0;
		for (Integer value : list) {
			result[(i++)] = value.intValue();
		}
		return result;
	}

	public static long[] longArray(Object[] array) {
		Long[] ll = toLongArray(array);
		return longArray(ll);
	}

	public static long[] longArray(Long[] array) {
		long[] result = new long[array.length];
		int i = 0;
		for (Long value : array) {
			result[(i++)] = value.longValue();
		}
		return result;
	}

	public static long[] longArray(List<Long> list) {
		return longArray((Long[]) list.toArray(new Long[0]));
	}

	public static boolean canParseInt(Object value) {
		try {
			Integer.parseInt(value.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean canParseDouble(Object value) {
		try {
			Double.parseDouble(value.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String round(Double d, int x) {
		DecimalFormat df = null;
		String formatString = "#";
		Double td = Double.valueOf(1.0D);
		if (x > 0) {
			td = Double.valueOf(Math.pow(10.0D, x));
			formatString = formatString + ".";
			for (int f = 1; f <= x; ++f) {
				formatString = formatString + "#";
			}
		}
		df = new DecimalFormat(formatString);
		Double xD = Double.valueOf(Math.floor(d.doubleValue() * td.doubleValue() + 0.5D));
		return df.format(xD.doubleValue() / td.doubleValue());
	}

}
