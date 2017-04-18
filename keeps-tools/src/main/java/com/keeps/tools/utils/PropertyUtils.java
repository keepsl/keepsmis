package com.keeps.tools.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement.Else;

public class PropertyUtils {
	private static final Log LOG = LogFactory.getLog(PropertyUtils.class);

	public static void main(String[] args) throws Exception {
		LOG.debug(getPropertyType(String.class, "bytes"));
	}

	public static Class<?> getPropertyType(Object target, String name) {
		try {
			return org.apache.commons.beanutils.PropertyUtils.getPropertyType(target, name);
		} catch (Exception e) {
			LOG.error("Object:[" + target.getClass() + "] property:[" + name + "] is error!");
			throw new RuntimeException(e);
		}
	}

	public static Class<?> getPropertyType(Class<?> targetClass, String name) {
		for (Field field : targetClass.getDeclaredFields()) {
			if (name.contains(".")) {
				if (name.startsWith(field.getName())) {
					return getPropertyType(field.getType(), name.substring(name.indexOf(".") + 1, name.length()));
				}

			} else if (name.equals(field.getName())) {
				return field.getType();
			}
		}

		throw new IllegalArgumentException("property name is error!");
	}

	public static Object getProperty(Object target, String name) {
		try {
			return org.apache.commons.beanutils.PropertyUtils.getProperty(target, name);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

	public static void setProperty(Object target, String name, Object value) {
		try {
			org.apache.commons.beanutils.PropertyUtils.setProperty(target, name, value);
		} catch (Exception e) {
			LOG.error("Object:[" + target.getClass() + "] property:[" + name + "] is error!");
			throw new RuntimeException(e);
		}
	}

	public static void setPropertyType(Object t, Map<String, Object> map) {
		Field[] pds = t.getClass().getDeclaredFields();
		if ((pds == null) || (pds.length <= 0)) {
			return;
		}
		Set mk = map.keySet();
		for (Field pd : pds) {
			String pd_name = pd.getName();
			if ("class".equals(pd_name))
				continue;
			if (!(isWriteable(t, pd_name))) {
				continue;
			}

			if (!(mk.contains(pd_name.toUpperCase()))) {
				continue;
			}
			Object value = map.get(pd_name.toUpperCase());

			setPropertyType(t, pd_name, value);
		}
	}

	public static void setPropertyType(Object t, String key, Object value) {
		Class type = getPropertyType(t, key);
		if (type == null) {
			return;
		}
		if (value == null) {
			setProperty(t, key, value);
		} else if (String.class.equals(type)) {
			if (value instanceof Clob)
				setProperty(t, key, StringUtils.ClobToString((Clob) value));
			else
				setProperty(t, key, String.valueOf(value));
		} else if (Integer.class.equals(type)) {
			setProperty(t, key, NumberUtils.toInteger(value));
		} else if (Long.class.equals(type)) {
			setProperty(t, key, NumberUtils.toLong(value));
		} else if (Double.class.equals(type)) {
			setProperty(t, key, NumberUtils.toDouble(value));
		} else if ((java.util.Date.class.equals(type)) || (java.sql.Date.class.equals(type))) {
			String v = ObjectUtils.nullSafeToEmptyString(value);
			if (v.indexOf("-") == -1) {
				setProperty(t, key, DateUtils.parseTimestamp(v));
			} else if (v.length() > 11)
				setProperty(t, key, DateUtils.parse(String.valueOf(value), "yyyy-MM-dd HH:mm:ss"));
			else {
				setProperty(t, key, DateUtils.parse(String.valueOf(value), "yyyy-MM-dd"));
			}

		} else if (Float.class.equals(type)) {
			setProperty(t, key, NumberUtils.toFloat(value));
		} else if (Boolean.class.equals(type)) {
			setProperty(t, key, value);
		} else if (BigDecimal.class.equals(type)) {
			setProperty(t, key, BigDecimal.valueOf(Double.valueOf(value.toString()).doubleValue()));
		} else {
			setProperty(t, key, value);
		}
	}

	public static PropertyDescriptor[] getPropertyDescriptors(Object target) {
		return org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(target);
	}

	public static PropertyDescriptor[] getPropertyDescriptors(Class targetClass) {
		return org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(targetClass);
	}

	public static boolean isReadable(Object target, String name) {
		return org.apache.commons.beanutils.PropertyUtils.isReadable(target, name);
	}

	public static boolean isWriteable(Object target, String name) {
		return org.apache.commons.beanutils.PropertyUtils.isWriteable(target, name);
	}

	public static void copyTo(Object from, Object to, String[] without) {
		copyTo(from, to, EditType.NULL_UPDATE, without);
	}

	public static void copyTo(Object from, Object to, EditType type, String[] without) {
		if ((from == null) || (to == null)) {
			return;
		}
		PropertyDescriptor[] propDescs = getPropertyDescriptors(from);

		Set excepts = null;

		if ((without != null) && (without.length > 0)) {
			excepts = new HashSet(without.length);

			for (String w : without) {
				excepts.add(w);
			}
		}

		PropertyDescriptor[] propTo = getPropertyDescriptors(to);

		Object propSet = new HashSet(propTo.length);
		for (PropertyDescriptor pt : propTo) {
			((Set) propSet).add(pt.getName());
		}

		for (PropertyDescriptor pd : propDescs) {
			String name = pd.getName();

			if (!(((Set) propSet).contains(name)))
				continue;
			if (!(isWriteable(from, name)))
				continue;
			if ((excepts != null) && (excepts.contains(name)))
				continue;
			if (name.equals("class"))
				continue;
			if (name.equals("id"))
				continue;
			Object value = getProperty(from, name);

			if ((EditType.NULL_UN_UPDATE.equals(type)) && (value == null)) {
				continue;
			}

			setProperty(to, name, value);
		}
	}

	public static void empty(Object entity) {
		PropertyDescriptor[] propDescs = getPropertyDescriptors(entity);

		for (PropertyDescriptor pd : propDescs) {
			String name = pd.getName();
			if (!(isWriteable(entity, name))) {
				continue;
			}
			if (!(name.equals("class"))) {
				if (pd.getPropertyType().isPrimitive()) {
					continue;
				}
				setProperty(entity, name, null);
			}
		}
	}
}
