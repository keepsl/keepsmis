package com.keeps.security.utils;


public class SSLUtil{
	
	/**
	  * @Title:			encrypt 
	  * @Description:	加密
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月9日
	 */
	public static String encrypt(String type, String key, String content) {
		try {
			if(type.equals("DES")){
				content = DesUtil.encrypt(content, key);
			}else if(type.equals("3DES")){
				content = ThreeDESUtil.Encrypt3DES(content, key);
			}else if(type.equals("AES")){
				content = AESUtil.encrypt(content, key);
			}
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	  * @Title:			decrypt 
	  * @Description:	解密
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年9月9日
	 */
	public static String decrypt(String type,String key, String content) {
		try {
			if(type.equals("DES")){
				content = DesUtil.decrypt(content.replaceAll(" ", "+"), key);
			}else if(type.equals("3DES")){
				content = ThreeDESUtil.Decrypt3DES(content.replaceAll(" ", "+"), key);
			}else if(type.equals("AES")){
				content = AESUtil.decrypt(content.replaceAll(" ", "+"), key);
			}
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
