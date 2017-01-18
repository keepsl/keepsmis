package com.keeps.security.utils;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.net.util.Base64;

public class ThreeDESUtil {
    public static void main(String[] args) throws Exception {
        String content = "{\"cmd\":\"saveParseCourseinfo\"}";
        String password = "12345678";
        System.out.println("密　钥：" + password);
        System.out.println("加密前：" + content);
        // 加密
        String encryptResult = Encrypt3DES(content, password);
        System.out.println("加密后：" + encryptResult);
        // 解密
        String decryptResult = Decrypt3DES(encryptResult, password);
        System.out.println("解密后：" + decryptResult);
    }
	// / <summary>

	// / 3des解码

	// / </summary>

	// / <param name="value">待解密字符串</param>

	// / <param name="key">原始密钥字符串</param>

	// / <returns></returns>

	public static String Decrypt3DES(String value, String key) throws Exception {

		byte[] b = decryptMode(GetKeyBytes(key), Base64.decodeBase64(value.getBytes()));

		return new String(b);

	}

	// / <summary>

	// / 3des加密

	// / </summary>

	// / <param name="value">待加密字符串</param>

	// / <param name="strKey">原始密钥字符串</param>

	// / <returns></returns>

	public static String Encrypt3DES(String value, String key) throws Exception {

		String str = byte2Base64(encryptMode(GetKeyBytes(key), value.getBytes()));

		return str;

	}

	// 计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位

	public static byte[] GetKeyBytes(String strKey) throws Exception {

		if (null == strKey || strKey.length() < 1)

			throw new Exception("key is null or empty!");

		java.security.MessageDigest alg = java.security.MessageDigest
				.getInstance("MD5");

		alg.update(strKey.getBytes());

		byte[] bkey = alg.digest();


		int start = bkey.length;

		byte[] bkey24 = new byte[24];

		for (int i = 0; i < start; i++) {

			bkey24[i] = bkey[i];

		}

		for (int i = start; i < 24; i++) {// 为了与.net16位key兼容

			bkey24[i] = bkey[i - start];

		}

		//System.out.println("byte24key.length=" + bkey24.length);

		//System.out.println("byte24key=" + byte2hex(bkey24));

		return bkey24;

	}

	private static final String Algorithm = "DESede"; // 定义 加密算法,可用
														// DES,DESede,Blowfish

	// keybyte为加密密钥，长度为24字节

	// src为被加密的数据缓冲区（源）

	public static byte[] encryptMode(byte[] keybyte, byte[] src) {

		try {

			// 生成密钥

			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密

			Cipher c1 = Cipher.getInstance(Algorithm);

			c1.init(Cipher.ENCRYPT_MODE, deskey);

			return c1.doFinal(src);

		} catch (java.security.NoSuchAlgorithmException e1) {

			e1.printStackTrace();

		} catch (javax.crypto.NoSuchPaddingException e2) {

			e2.printStackTrace();

		} catch (java.lang.Exception e3) {

			e3.printStackTrace();

		}

		return null;

	}

	// keybyte为加密密钥，长度为24字节

	// src为加密后的缓冲区

	public static byte[] decryptMode(byte[] keybyte, byte[] src) {

		try { // 生成密钥

			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 解密

			Cipher c1 = Cipher.getInstance(Algorithm);

			c1.init(Cipher.DECRYPT_MODE, deskey);

			return c1.doFinal(src);

		} catch (java.security.NoSuchAlgorithmException e1) {

			e1.printStackTrace();

		} catch (javax.crypto.NoSuchPaddingException e2) {

			e2.printStackTrace();

		} catch (java.lang.Exception e3) {

			e3.printStackTrace();

		}

		return null;

	}

	// 转换成base64编码

	public static String byte2Base64(byte[] b) {

		return Base64.encodeBase64String(b);

	}

	// 转换成十六进制字符串

	public static String byte2hex(byte[] b) {

		String hs = "";

		String stmp = "";

		for (int n = 0; n < b.length; n++) {

			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

			if (stmp.length() == 1)

				hs = hs + "0" + stmp;

			else

				hs = hs + stmp;

			if (n < b.length - 1)

				hs = hs + ":";

		}

		return hs.toUpperCase();

	}

}