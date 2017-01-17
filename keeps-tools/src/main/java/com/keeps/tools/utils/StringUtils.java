package com.keeps.tools.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

public class StringUtils {
	protected static final Log log = LogFactory.getLog(StringUtils.class);
	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String UTF_8 = "UTF-8";

	public static final boolean notText(StringBuffer sb) {
		return (!(hasText(sb)));
	}

	public static final boolean hasText(StringBuffer sb) {
		return ((sb != null) && (hasText(sb.toString())));
	}

	public static final boolean notText(String str) {
		return (!(hasText(str)));
	}

	public static final boolean hasText(String str) {
		if ((str == null) || ("".equals(str)))
			return false;

		return (str.trim().length() > 0);
	}

	public static boolean hasLength(String str) {
		if (str == null)
			return false;
		return ((str != null) && (str.length() > 0));
	}

	public static boolean hasLength(String[] strs) {
		if (strs == null)
			return false;

		return (strs.length > 0);
	}

	public static int countSubstring(String str, String sub) {
		return org.springframework.util.StringUtils.countOccurrencesOf(str, sub);
	}

	public static void bulkUpdate(String[] array, String leading, String trailing) {
		if (ArrayUtils.isEmpty(array)) {
			return;
		}
		if (leading == null)
			leading = "";
		if (trailing == null)
			trailing = "";

		for (int i = 0; i < array.length; ++i)
			array[i] = leading + ((array[i] == null) ? "" : array[i]) + trailing;
	}

	public static String[] split(String str, String regex) {
		Assert.hasText(regex);
		if (notText(str)) {
			return new String[0];
		}
		return str.split(regex);
	}

	public static String[] trimAll(String[] array) {
		if (array == null)
			return array;
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; ++i) {
			result[i] = ((array[i] == null) ? null : array[i].trim());
		}
		return result;
	}

	public static <T> String arrayToString(T[] array) {
		return arrayToString(array, ",", "", "");
	}

	public static <T> String arrayToString(T[] array, String connectSymbol) {
		return arrayToString(array, connectSymbol, "", "");
	}

	public static <T> String arrayToString(T[] array, String leading, String trailing) {
		return arrayToString(array, ",", leading, trailing);
	}

	public static <T> String arrayToString(T[] array, String connectSymbol, String leading, String trailing) {
		connectSymbol = (connectSymbol == null) ? "" : connectSymbol;
		leading = (leading == null) ? "" : leading;
		trailing = (trailing == null) ? "" : trailing;
		int len = array.length;
		if (len == 0)
			return "";
		StringBuffer buf = new StringBuffer(len * 12 + leading.length() + trailing.length());
		for (int i = 0; i < len - 1; ++i) {
			buf.append(leading).append(array[i].toString()).append(trailing).append(connectSymbol);
		}
		return leading + array[(len - 1)].toString() + trailing;
	}

	public static String convertStrByCoding(String str) {
		return convertStrByCoding(str, "ISO-8859-1");
	}

	public static String convertStrByCoding(String str, String coding) {
		try {
			return new String(str.getBytes(coding));
		} catch (Exception e) {
			log.error("string coding is error!", e);
		}
		return null;
	}

	public static String converStrByCoding(String str, String code1, String code2) {
		try {
			if (notText(str)) {
				return "";
			}
			return new String(str.getBytes(code1), code2);
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	public static boolean nullSafeEquals(String s1, String s2) {
		return ObjectUtils.nullSafeEquals(s1, s2);
	}

	public static String replace(String inString, String oldPattern, String newPattern) {
		if (inString == null) {
			return null;
		}
		if ((oldPattern == null) || (newPattern == null)) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();

		int pos = 0;
		int index = inString.indexOf(oldPattern);

		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		return sbuf.toString();
	}

	public static String collectionToString(Collection<?> coll, String connectSymbol) {
		return collectionToString(coll, connectSymbol, "", "");
	}

	public static String collectionToString(Collection<?> coll, String connectSymbol, String leading, String trailing) {
		return arrayToString(coll.toArray(), connectSymbol, leading, trailing);
	}

	public static List<String> collectionToStringList(Collection<?> coll) {
		if ((coll == null) || (coll.size() == 0))
			return new ArrayList(0);
		List result = new ArrayList((coll.size() > 10000) ? 10000 : coll.size());

		for (Iterator localIterator = coll.iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			if ((o instanceof String) || (o instanceof Number))
				result.add(o.toString());
			else if (o instanceof Date) {
				result.add(DateUtils.format((Date) o));
			}
		}
		return result;
	}

	public static String[] addToArray(String[] array, String[] strs) {
		return addToArray(array, 0, strs);
	}

	public static String[] addToArray(String[] array, int index, String[] strs) {
		if ((strs == null) || (strs.length == 0))
			return array;

		if ((index < 0) || (index > array.length)) {
			throw new IndexOutOfBoundsException("索引:[" + index + "]小于0 或�?? 大于数组长度: [" + array.length + "]");
		}

		String[] result = new String[array.length + strs.length];

		System.arraycopy(array, 0, result, 0, index);
		System.arraycopy(strs, 0, result, index, strs.length);
		System.arraycopy(array, index, result, index + strs.length, array.length - index);

		return result;
	}

	public static String outByTimes(String s, int times) {
		if (s == null)
			return s;

		StringBuilder builder = new StringBuilder((s.length() * times > 2000) ? 2000 : s.length() * times);
		for (int i = 0; i < times; ++i) {
			builder.append(s);
		}
		return builder.toString();
	}

	public static String removeChars(String s, char[] chars) {
		if ((s == null) || (chars == null))
			return s;
		String temp = new String(s);
		for (char c : chars) {
			temp = replace(temp, String.valueOf(c), "");
		}
		return temp;
	}

	public static String toUtf8(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if ((c >= 0) && (c <= 255)) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; ++j) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}

		return sb.toString();
	}

	public static String getPYSimpleName(String a) {
		if (!(hasText(a)))
			return "";
		char[] chars = a.toCharArray();
		StringBuffer strBuffer = new StringBuffer(chars.length);
		for (int i = 0; i < chars.length; ++i) {
			strBuffer.append(getPYHeadLetter(chars[i]));
		}
		return strBuffer.toString();
	}

	public static char getPYHeadLetter(char aChar) {
		int charAscii = getCharAscii(aChar);
		char result;
		if ((charAscii >= 0) && (charAscii <= 255)) {
			result = aChar;
		} else if ((charAscii >= 45217) && (charAscii <= 45252)) {
			result = 'A';
		} else if ((charAscii >= 45253) && (charAscii <= 45760)) {
			result = 'B';
		} else if ((charAscii >= 45761) && (charAscii <= 46317)) {
			result = 'C';
		} else if ((charAscii >= 46318) && (charAscii <= 46825)) {
			result = 'D';
		} else if ((charAscii >= 46826) && (charAscii <= 47009)) {
			result = 'E';
		} else if ((charAscii >= 47010) && (charAscii <= 47296)) {
			result = 'F';
		} else if ((charAscii >= 47297) && (charAscii <= 47613)) {
			result = 'G';
		} else if ((charAscii >= 47614) && (charAscii <= 48118)) {
			result = 'H';
		} else if ((charAscii >= 48119) && (charAscii <= 49061)) {
			result = 'J';
		} else if ((charAscii >= 49062) && (charAscii <= 49323)) {
			result = 'K';
		} else if ((charAscii >= 49324) && (charAscii <= 49895)) {
			result = 'L';
		} else if ((charAscii >= 49896) && (charAscii <= 50370)) {
			result = 'M';
		} else if ((charAscii >= 50371) && (charAscii <= 50613)) {
			result = 'N';
		} else if ((charAscii >= 50614) && (charAscii <= 50621)) {
			result = 'O';
		} else if ((charAscii >= 50622) && (charAscii <= 50905)) {
			result = 'P';
		} else if ((charAscii >= 50906) && (charAscii <= 51386)) {
			result = 'Q';
		} else if ((charAscii >= 51387) && (charAscii <= 51445)) {
			result = 'R';
		} else if ((charAscii >= 51446) && (charAscii <= 52217)) {
			result = 'S';
		} else if ((charAscii >= 52218) && (charAscii <= 52697)) {
			result = 'T';
		} else if ((charAscii >= 52698) && (charAscii <= 52979)) {
			result = 'W';
		} else if ((charAscii >= 52980) && (charAscii <= 53640)) {
			result = 'X';
		} else if ((charAscii >= 53689) && (charAscii <= 54480)) {
			result = 'Y';
		} else if ((charAscii >= 54481) && (charAscii <= 62289)) {
			result = 'Z';
		} else {
			log.warn("无法解析字母[" + aChar + "]Ascii[" + charAscii + "]");
			result = ' ';
		}

		return Character.toLowerCase(result);
	}

	public static String parse(Double d) {
		return parse(d, 0);
	}

	public static String parse(Object o, int m) {
		if (m < 0)
			throw new RuntimeException("格式不对");

		String formatString = "0";
		if (m > 0) {
			formatString = formatString + ".";
		}
		for (int i = 1; i <= m; ++i) {
			formatString = formatString + "0";
		}
		DecimalFormat df = new DecimalFormat(formatString);

		return df.format(o);
	}

	public static int getCharAscii(char aChar) {
		byte[] bytes = String.valueOf(aChar).getBytes();
		if ((bytes == null) || (bytes.length > 2) || (bytes.length <= 0)) {
			return 0;
		}

		if (bytes.length == 1) {
			return bytes[0];
		}
		if (bytes.length == 2) {
			int hightByte = 256 + bytes[0];
			int lowByte = 256 + bytes[1];
			int ascii = 256 * hightByte + lowByte;
			return ascii;
		}
		log.warn("无法获得字符[" + aChar + "]的Ascii编码");
		return 0;
	}

	public static String trimText(String text) {
		if (hasText(text)) {
			return text.trim();
		}
		return "";
	}

	public static boolean isExsit(String in, String[] v) {
		if (v == null) {
			return false;
		}
		for (String vl : v) {
			if (vl.equals(in)) {
				return true;
			}
		}
		return false;
	}

	public static String ClobToString(Clob clob) {
		String reString = "";
		Reader is = null;
		BufferedReader br = null;
		try {
			if (clob == null) {
				return "";
			}
			is = clob.getCharacterStream();
			br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (s != null) {
				sb.append(s);
				s = br.readLine();
			}
			reString = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("clob转换过程中出错");
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reString;
	}

	public static void appendValueHasText(Object v, StringBuffer sb, String s) {
		Boolean yes = Boolean.valueOf(false);
		if (v instanceof String)
			yes = Boolean.valueOf(hasText((String) v));
		else {
			yes = Boolean.valueOf(v != null);
		}
		if (yes.booleanValue())
			sb.append(" " + s + " ");
	}

	public static void appendValueHasTextPut(Object v, StringBuffer sb, String s, List vl) {
		Boolean yes = Boolean.valueOf(false);
		if (v instanceof String)
			yes = Boolean.valueOf(hasText((String) v));
		else {
			yes = Boolean.valueOf(v != null);
		}
		if (yes.booleanValue()) {
			sb.append(" " + s + " ");
			vl.add(v);
		}
	}

	public abstract class Symbol {
		public static final String EMPTY = "";
		public static final String POINT = ".";
		public static final String COMMA = ",";
		public static final String SLASH = "/";
		public static final String BACKSLASH = "\\";
		public static final String QM = "'";
		public static final String DQM = "\"";
		public static final String BLANK_SBC = "　";
		public static final String BLANK_DBC = " ";
		public static final String FH = ":";
		public static final String LINE_ = "_";
		public static final String M = "*";
		public static final String WH = "?";
		public static final String JH = "#";
	}

}
