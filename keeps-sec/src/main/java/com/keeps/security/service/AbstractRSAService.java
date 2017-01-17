package com.keeps.security.service;

import com.keeps.tools.exception.CapecException;
import com.keeps.security.SecurityService;
import com.keeps.security.utils.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public abstract class AbstractRSAService implements SecurityService {
	protected byte[] encryptPub(RSAPublicKey publicKey, byte[] pubkey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(1, publicKey);

		return encrypt(pubkey, cipher);
	}

	protected byte[] encryptPri(RSAPrivateKey privateKey, byte[] priKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(1, privateKey);
		return encrypt(priKey, cipher);
	}

	private byte[] encrypt(byte[] srcbyte, Cipher cipher)
			throws IOException, IllegalBlockSizeException, BadPaddingException {
		InputStream ins = new ByteArrayInputStream(srcbyte);
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		byte[] buf = new byte[100];
		int bufl;
		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; ++i) {
					block[i] = buf[i];
				}
			}
			writer.write(cipher.doFinal(block));
		}
		return writer.toByteArray();
	}

	public String decrptByPrivate(byte[] encrpt, String privateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException,
			IOException, InvalidKeyException, NoSuchPaddingException {
		byte[] privateByte = Base64.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateByte);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(2, privateK);
		return decrypt(encrpt, cipher);
	}

	public String decrptByPublic(byte[] encrpt, String publicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException,
			IOException, InvalidKeyException, NoSuchPaddingException {
		byte[] publicByte = Base64.decode(publicKey);
		X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(publicByte);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicK = keyFactory.generatePublic(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(2, publicK);
		return decrypt(encrpt, cipher);
	}

	private String decrypt(byte[] srcbyte, Cipher cipher)
			throws IOException, IllegalBlockSizeException, BadPaddingException {
		InputStream ins = new ByteArrayInputStream(srcbyte);
		ByteArrayOutputStream writer = new ByteArrayOutputStream();
		byte[] buf = new byte[128];
		int bufl;
		while ((bufl = ins.read(buf)) != -1) {
			byte[] block = null;

			if (buf.length == bufl) {
				block = buf;
			} else {
				block = new byte[bufl];
				for (int i = 0; i < bufl; ++i) {
					block[i] = buf[i];
				}
			}
			writer.write(cipher.doFinal(block));
		}
		return new String(writer.toByteArray(), "utf-8");
	}

	public String encode(String s) {
		return encodePubKey(getPubKey(), s);
	}

	public String decode(String s) {
		return decodePriKey(getPriKey(), s);
	}

	public String encodePubKey(String pubkey, String s) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(pubkey)));
			byte[] resultMes = encryptPub((RSAPublicKey) publicKey, s.getBytes());
			return Base64.encode(resultMes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CapecException("secen过程中出现异常!");
		}
	}

	public String decodePriKey(String prikey, String s) {
		try {
			return decrptByPrivate(Base64.decode(s), prikey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CapecException("secde过程中出现异常!");
		}
	}

	public String encodePriKey(String priKey, String s) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(priKey)));
			byte[] resultMes = encryptPri((RSAPrivateKey) privateKey, s.getBytes());
			return Base64.encode(resultMes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CapecException("secen过程中出现异常!");
		}
	}

	public String decodePubKey(String pubKey, String s) {
		try {
			return decrptByPublic(Base64.decode(s), pubKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CapecException("secde过程中出现异常!");
		}
	}

	protected abstract String getPubKey();

	protected abstract String getPriKey();
}