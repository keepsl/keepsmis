package com.keeps.tools.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {

	public static long randomLong() {
		return UUID.randomUUID().getMostSignificantBits();
	}

	public static String randomUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static long randomABSLong() {
		return Math.abs(randomLong());
	}

	public static String randomByYMDHMS(int n) {
		String rs = DateUtils.formatNow("yyyyMMddHHmmss");
		if (n > 0) {
			rs = rs + randomNumByLength(n);
		}
		return rs;
	}

	public static String randomString4Int(int digit) {
		Assert.isTrue(digit > 0);
		Random random = new Random();
		StringBuilder re = new StringBuilder(digit);
		for (int i = 0; i < digit; i++) {
			re.append(random.nextInt(10));
		}
		return re.toString();
	}

	public static String randomString4Letter(int digit) {
		Assert.isTrue(digit > 0);
		Random random = new Random();
		StringBuilder re = new StringBuilder(digit);
		for (int i = 0; i < digit; i++) {
			boolean isBig = random.nextInt(2) == 1;
			if (isBig) {
				re.append((char) (random.nextInt(26) + 65));
			} else {
				re.append((char) (random.nextInt(26) + 97));
			}
		}
		return re.toString();
	}

	public static String randomNumByLength(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
	}
}
