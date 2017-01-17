package com.keeps.tools.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class MD5Util {

	private Logger log = Logger.getLogger(MD5Util.class);

	private static MD5Util instance = new MD5Util();

	public static MD5Util getInstance() {
		return instance;
	}

	public String toMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; ++offset) {
				int i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			return buf.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			this.log.error(e);
		}
		return "";
	}

}
