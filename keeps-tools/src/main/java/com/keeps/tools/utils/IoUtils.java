package com.keeps.tools.utils;

import com.keeps.tools.common.SoftUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
/**
 * <p>
 * Title: IoUtils.java
 * </p>
 * <p>
 * Description: @TODO
 * </p>
 * <p>
 * Copyright: Copyright (c) KEEPS
 * </p>
 * 
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日 修改日期： 修改人： 复审人：
 */
public class IoUtils implements SoftUtils {
	public static void close(InputStream is) {
		try {
			if (is != null)
				is.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void close(OutputStream os) {
		try {
			if (os != null)
				os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void flush(OutputStream os) {
		try {
			if (os != null)
				os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String converEncoding(String s, String en_1, String en_2) {
		en_1 = (en_1 == null) ? "ISO-8859-1" : en_1;
		en_2 = (en_2 == null) ? "UTF-8" : en_2;
		try {
			return new String(s.getBytes(en_1), en_2);
		} catch (UnsupportedEncodingException e) {
		}
		return s;
	}
}
