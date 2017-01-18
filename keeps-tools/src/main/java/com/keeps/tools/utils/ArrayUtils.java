package com.keeps.tools.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ArrayUtils {
	protected static final Log log = LogFactory.getLog(ArrayUtils.class);

	public static void replace(Object[] array, Object oldObj, Object newObj) {
		if (oldObj == null) {
			oldObj = Null.getInstance();
		}

		for (int i = 0; i < array.length; ++i)
			if (oldObj.equals(array[i]))
				array[i] = newObj;
	}

	public static String[] conver2String(Object[] array) {
		String[] rs = new String[array.length];
		for (int i = 0; i < array.length; ++i) {
			rs[i] = String.valueOf((array[i] == null) ? "" : array[i]);
		}

		return rs;
	}

	public static <T> T[] delectToStringEmptyValues(T[] array) {
		if (array == null)
			return array;

		List list = new ArrayList();
		for (Object o : array) {
			if (o == null) {
				continue;
			}
			if (StringUtils.hasText(o.toString())) {
				list.add(o);
			}
		}
		Object[] result = (Object[]) Array.newInstance(array.getClass().getComponentType(), list.size());
		return (T[]) list.toArray(result);
	}

	public static <T> T[] delectNullValues(T[] array) {
		if (array == null)
			return array;

		List list = new ArrayList();
		for (Object o : array) {
			if (o == null) {
				continue;
			}
			list.add(o);
		}
		Object[] result = (Object[]) Array.newInstance(array.getClass().getComponentType(), list.size());
		return (T[]) list.toArray(result);
	}

	public static <T> T[] delectEqualValues(T[] array) {
		if (array == null)
			return array;

		Set set = new HashSet(array.length);

		for (Object t : array) {
			set.add(t);
		}

		Object[] result = (Object[]) Array.newInstance(array.getClass().getComponentType(), set.size());
		return (T[]) set.toArray(result);
	}

	public static boolean isEmpty(int[] array) {
		return ((array == null) || (array.length == 0));
	}

	public static boolean isEmpty(Object[] array) {
		return ((array == null) || (array.length == 0));
	}

	public static int indexOf(int[] array, int oo) {
		if (array == null)
			return -1;
		for (int i = 0; i < array.length; ++i) {
			if (array[i] == oo)
				return i;
		}
		return -1;
	}

	public static int indexOf(Object[] array, Object o) {
		if (array == null)
			return -1;
		for (int i = 0; i < array.length; ++i) {
			if (array[i].equals(o))
				return i;
		}
		return -1;
	}

	public static boolean contains(Object[] array, Object o) {
		return (indexOf(array, o) > -1);
	}

	public static <T> T[] copyArray(T[] src) {
		if (src == null) {
			return null;
		}
		int length = src.length;
		Object[] result = (Object[]) Array.newInstance(src.getClass().getComponentType(), length);
		System.arraycopy(src, 0, result, 0, length);
		return (T[]) result;
	}

	public static <T> T[] addObjectsToArray(T[] array, T[] otherObjs) {
		if ((otherObjs == null) || (array == null))
			return array;
		return addObjectsToArray(array, array.length, otherObjs);
	}

	public static <T> T[] addObjectsToArray(T[] array, int index, T[] otherObjs) {
		if ((otherObjs == null) || (array == null))
			throw new NullPointerException();

		if ((index < 0) || (index > array.length)) {
			throw new IndexOutOfBoundsException("索引:[" + index + "]小于0 或�?? 大于数组长度: [" + array.length + "]");
		}

		Object[] result = (Object[]) Array.newInstance(array.getClass().getComponentType(),
				array.length + otherObjs.length);

		System.arraycopy(array, 0, result, 0, index);
		System.arraycopy(otherObjs, 0, result, index, otherObjs.length);
		System.arraycopy(array, index, result, index + otherObjs.length, array.length - index);

		return (T[]) result;
	}

	public static Object[] mergeObjectsToArray(Object[] objs) {
		if (objs == null)
			return objs;

		List list = new ArrayList();

		for (Object obj : objs) {
			if (addIntoList(list, obj))
				continue;
			if (obj.getClass().isArray()) {
				for (int i = 0; i < Array.getLength(obj); ++i) {
					Object temp = Array.get(obj, i);
					if (!(addIntoList(list, temp)))
						throw new UnsupportedOperationException(
								"mergeObjectsToArray 不支持类型[" + ObjectUtils.nullSafeClassName(obj) + "]");
				}
			} else {
				throw new UnsupportedOperationException(
						"mergeObjectsToArray 不支持类型[" + ObjectUtils.nullSafeClassName(obj) + "]");
			}
		}

		return list.toArray();
	}

	private static boolean addIntoList(List<Object> list, Object obj) {
		if (obj == null) {
			list.add(null);
			return true;
		}
		if ((obj instanceof String) || (obj instanceof Number) || (obj instanceof Date)) {
			list.add(obj);
			return true;
		}
		return false;
	}

	public static <T> T safeGet(T[] array, int index) {
		if ((array == null) || (index < 0) || (index > array.length - 1)) {
			return null;
		}
		return array[index];
	}

	public static Object newInstanceWithFill(Class<?> componentType, int length, Object filledValue) {
		Object array = Array.newInstance(componentType, length);
		for (int i = 0; i < Array.getLength(array); ++i) {
			Array.set(array, i, filledValue);
		}
		return array;
	}

	public static String[] removeNullValue(String[] strings) {
		List list = new ArrayList(strings.length);
		for (String s : strings) {
			if (StringUtils.notText(s)) {
				continue;
			}
			list.add(s);
		}
		return ((String[]) list.toArray(new String[list.size()]));
	}

	public static void main(String[] args) {
	}
}
