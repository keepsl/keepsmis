package com.keeps.tools.utils;

import com.keeps.tools.exception.CapecErrorException;
import com.keeps.tools.exception.CapecException;

public class Assert {
	public static void isTrue(boolean exp) {
		isTrue(exp, "执行过程中出现未知错误,请联系服务人员!");
	}

	public static void isTrue(boolean exp, String message) {
		isTrue(exp, message, false);
	}

	public static void isTrue(boolean exp, String message, boolean isError) {
		if (!exp) {
			if (!(isError)) {
				throw new CapecException(message);
			}
			throw new CapecErrorException(message);
		}
	}
}
