package com.keeps.security.service;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.keeps.security.SecurityService;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SecurityDESServiceImpl implements SecurityService {
	private static final String Algorithm = "DESede";
	private static final byte[] keyBytes = { 23, 35, 79, 88, -120, 16, 64, 56, 40, 37, 121, 81, -53, -35, 85, 102, 119,
			41, 116, -104, 41, 68, 54, -30 };

	private static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");

			Cipher c1 = Cipher.getInstance("DESede");
			c1.init(1, deskey);
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			SecretKey deskey = new SecretKeySpec(keybyte, "DESede");

			Cipher c1 = Cipher.getInstance("DESede");
			c1.init(2, deskey);
			return c1.doFinal(src);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; ++n) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n >= b.length - 1)
				continue;
			hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public String encode(String s) {
		Base64 base64 = new Base64();  
        byte[] enbytes = base64.encodeBase64Chunked(s.getBytes());  
        return new String(enbytes);  
		/*byte[] encoded = encryptMode(keyBytes, s.getBytes());
		BASE64Encoder enc = new BASE64Encoder();
		String cipherString = enc.encode(encoded);
		return cipherString;*/
	}

	public String decode(String s) {
		if (StringUtils.isBlank(s))
			return "";
		try {
			s = s.replace(" ", "+");
			Base64 base64 = new Base64();  
	        byte[] debytes = base64.decodeBase64(new String(s).getBytes());  
	        return new String(debytes);
			/*BASE64Decoder dec = new BASE64Decoder();
			byte[] srcBytes = decryptMode(keyBytes, dec.decodeBuffer(s));
			return new String(srcBytes, Charset.forName("utf-8"));*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String encodePubKey(String pubkey, String s) {
		return encode(s);
	}

	public String encodePriKey(String priKey, String s) {
		return encode(s);
	}

	public String decodePubKey(String pubKey, String s) {
		return decode(s);
	}

	public String decodePriKey(String prikey, String s) {
		return decode(s);
	}

	public static void main(String[] args) {
		SecurityDESServiceImpl im = new SecurityDESServiceImpl();

		System.out.println(im.encode("ALDKJAKDSAV2342"));
	}
}
