package com.keeps.security.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.net.util.Base64;


public class DesUtil {
    
	public static void main(String args[]) throws Exception {
		// 待加密内容
		String str = "12345678";
		// 密码，长度要是8的倍数
		//String password = "9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";
		String password = "12345678";
		String result = encrypt(str,password);
		System.out.println("加密后：" + result);

		// 直接将如上内容解密
		try {
			String decryResult = decrypt(result, password);
			System.out.println("解密后：" + decryResult);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
	
    public static String encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
 
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
 
        return encodeBase64(cipher.doFinal(message.getBytes("UTF-8")));
    }
 
    public static String decrypt(String message, String key) throws Exception {
 
        byte[] bytesrc = decodeBase64(message);//convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
 
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
 
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte,"UTF-8");
    }
 
    public static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
 
        return digest;
    }
 
    public static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
 
        return hexString.toString();
    }
 
     
    public static String encodeBase64(byte[] b) throws Exception {
        //return  EcardServiceUtils.encodeBase64(b);
    	return Base64.encodeBase64String(b);
    }
     
    public static byte[] decodeBase64(String base64String) throws Exception {
        return Base64.decodeBase64(base64String);
    }
}
