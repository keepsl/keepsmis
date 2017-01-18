package com.keeps.tools.utils;

import org.springframework.util.Assert;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HqlUtils {

	private static final String[] COLLECT_FUNCTION_START = { "sum(", "count(", "avg(" };

	public static void main(String[] args) {
	}

	public static String applyQueryStringParameters(String queryString, Object[] values) {
		String qm = "?";
		int count = StringUtils.countSubstring(queryString, qm);
		int len = values == null ? 0 : values.length;
		Assert.isTrue(count == len);

		StringBuffer str = new StringBuffer(queryString);
		for (int i = 0; i < count; i++) {
			String obj = values == null ? "null" : values[i].toString();
			str.replace(str.indexOf(qm), str.indexOf(qm) + qm.length(), '\'' + obj + '\'');
		}
		return str.toString();
	}

	public static String getFromPersistentClass(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");

		Pattern p = Pattern.compile("from\\s*[a-zA-Z1-9]*\\s*", 2);
		String[] removes = p.split(hql);

		String fromEntityName = hql;
		String[] arrayOfString1;
		int j = (arrayOfString1 = removes).length;
		for (int i = 0; i < j; i++) {
			String remove = arrayOfString1[i];
			fromEntityName = StringUtils.replace(fromEntityName, remove, "");
		}
		fromEntityName = StringUtils.replace(fromEntityName, "from", "").trim();
		if (fromEntityName.indexOf(" ") > -1) {
			fromEntityName = fromEntityName.substring(0, fromEntityName.indexOf(" "));
		}
		return fromEntityName;
	}

	public static String getFromAlias(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");

		Pattern p = Pattern.compile("from\\s*[a-zA-Z1-9]*\\s*[a-zA-Z1-9]*\\s*", 2);
		String[] removes = p.split(hql);

		String fromEntityName = hql;
		String[] arrayOfString1;
		int j = (arrayOfString1 = removes).length;
		for (int i = 0; i < j; i++) {
			String remove = arrayOfString1[i];
			fromEntityName = StringUtils.replace(fromEntityName, remove, "");
		}
		fromEntityName = StringUtils.replace(fromEntityName, "from", "");
		String className = getFromPersistentClass(hql);
		fromEntityName = StringUtils.replace(fromEntityName, className, "").trim();
		return fromEntityName;
	}

	public static String arrayToString(String[] array) {
		return arrayToString(array, ",", "", "");
	}

	public static String arrayToString(String[] array, String leading) {
		return arrayToString(array, ",", leading, "");
	}

	private static <T> String arrayToString(String[] array, String connectSymbol, String leading, String trailing) {
		connectSymbol = connectSymbol == null ? "" : connectSymbol;
		leading = leading == null ? "" : leading;
		trailing = trailing == null ? "" : trailing;
		int len = array.length;
		if (len == 0) {
			return "";
		}
		StringBuffer buf = new StringBuffer(len * 12 + leading.length() + trailing.length());
		for (int i = 0; i < len - 1; i++) {
			String s = array[i].toString();
			if (notAddLeading(s)) {
				buf.append(s).append(connectSymbol);
			} else {
				buf.append(leading).append(s).append(trailing).append(connectSymbol);
			}
		}
		if (!notAddLeading(array[(len - 1)])) {
			buf.append(leading);
		}
		buf.append(array[(len - 1)]).append(trailing);

		return buf.toString();
	}

	private static boolean notAddLeading(String str) {
		String[] arrayOfString;
		int j = (arrayOfString = COLLECT_FUNCTION_START).length;
		int i = 0;
		for (i = 0; i < j; i++) {
			String one = arrayOfString[i];
			if ((str != null) && (str.trim().toLowerCase().startsWith(one))) {
				return true;
			}
		}
		j = (arrayOfString = new String[] { "(case ", "case ", "case", "\\s*case\\s*(" }).length;
		for (i = 0; i < j; i++) {
			String one = arrayOfString[i];
			if ((str != null) && (str.trim().toLowerCase().startsWith(one))) {
				return true;
			}
		}
		j = (arrayOfString = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" }).length;
		for (i = 0; i < j; i++) {
			String one = arrayOfString[i];
			if ((str != null) && (str.trim().startsWith(one))) {
				return true;
			}
		}
		j = (arrayOfString = new String[] { "'" }).length;
		for (i = 0; i < j; i++) {
			String one = arrayOfString[i];
			if ((str != null) && (str.trim().startsWith(one))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isContainFunction(String s) {
		return isContainCollectFunction(s);
	}

	public static boolean isContainCollectFunction(String str) {
		String[] arrayOfString;
		int j = (arrayOfString = COLLECT_FUNCTION_START).length;
		for (int i = 0; i < j; i++) {
			String one = arrayOfString[i];
			if ((str != null) && (StringUtils.countSubstring(str.toLowerCase(), one) > 0)) {
				return true;
			}
		}
		return false;
	}

	public static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	public static String removeFrom(String hql) {
		Assert.hasText(hql);
		throw new UnsupportedOperationException();
	}

	public static String removeWhere(String hql) {
		Assert.hasText(hql);
		throw new UnsupportedOperationException();
	}

	public static String removeOrderBys(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static String removeGroupBys(String hql) {
		Assert.hasText(hql);
		Pattern notOrdeyP = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Pattern hasOrderP = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]*order\\s*by", 2);
		Matcher notOrdeyM = notOrdeyP.matcher(hql);
		Matcher hasOrderM = hasOrderP.matcher(hql);
		StringBuffer sb = new StringBuffer();

		boolean ok = false;
		while (hasOrderM.find()) {
			ok = true;
			hasOrderM.appendReplacement(sb, "order by");
		}
		if (ok) {
			hasOrderM.appendTail(sb);
		}
		if (!ok) {
			while (notOrdeyM.find()) {
				notOrdeyM.appendReplacement(sb, "");
			}
			notOrdeyM.appendTail(sb);
		}
		return sb.toString();
	}
}
