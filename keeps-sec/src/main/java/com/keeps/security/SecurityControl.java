package com.keeps.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.keeps.tools.exception.CapecException;
import com.keeps.security.service.SecurityDESServiceImpl;
import com.keeps.security.service.SecurityRSA1ServiceImpl;
import com.keeps.security.service.SecurityRSA2ServiceImpl;

public class SecurityControl {
	private Log log = LogFactory.getLog(super.getClass());
	private static SecurityControl INSTANCE = new SecurityControl();
	private static Random R = new Random();
	protected static Map<String, SecurityService> D = new HashMap<String, SecurityService>();
	static {
		register("Rc", new SecurityRSA1ServiceImpl());
		register("mc", new SecurityRSA1ServiceImpl());

		register("A1", new SecurityDESServiceImpl());
		register("3a", new SecurityDESServiceImpl());

		register("Mc", new SecurityRSA2ServiceImpl());
		register("ew", new SecurityRSA2ServiceImpl());
	}

	private static synchronized void register(String k, SecurityService clazz) {
		if (!(D.containsKey(k)))
			D.put(k, clazz);
		else
			throw new CapecException("加密类注册的值重复");
	}

	public static SecurityControl getInstance() {
		return INSTANCE;
	}

	public String encode(String s) {
		if (StringUtils.isBlank(s))
			return "";
		try {
			int t = R.nextInt(D.size());
			String mkey = "";
			int i = 0;
			for (String k : D.keySet()) {
				if (i == t) {
					mkey = k;
					break;
				}
				++i;
			}
			SecurityService service = (SecurityService) D.get(mkey);
			return mkey + service.encode(s);
		} catch (Exception e) {
			e.printStackTrace();

			throw new CapecException("sec出错");
		}
	}

	public String decode(String s) {
		if (StringUtils.isBlank(s))
			return "";
		try {
			String fis = s.substring(0, 2);
			SecurityService service = (SecurityService) D.get(fis);
			if (service == null) {
				this.log.error("解密:" + s + "字符串时出错，未了现对应的解密方法，返回null");
				return null;
			}
			return service.decode(s.substring(2, s.length()));
		} catch (Exception e) {
			e.printStackTrace();

			throw new CapecException("de出错");
		}
	}

	public String encodePubKey(String pubkey, String s) {
		if (StringUtils.isBlank(s))
			return "";
		try {
			String mkey = "Rc";
			SecurityService service = (SecurityService) D.get(mkey);
			return mkey + service.encodePubKey(pubkey, s);
		} catch (Exception e) {
			e.printStackTrace();

			throw new CapecException("sec出错");
		}
	}

	public String decodePriKey(String prikey, String s) {
		if (StringUtils.isBlank(s))
			return "";
		try {
			String fis = s.substring(0, 2);
			SecurityService service = (SecurityService) D.get(fis);
			if (service == null) {
				this.log.error("解密:" + s + "字符串时出错，未了现对应的解密方法，返回null");
				return null;
			}
			return service.decodePriKey(prikey, s.substring(2, s.length()));
		} catch (Exception e) {
			e.printStackTrace();

			throw new CapecException("de出错");
		}
	}

	public String encodePriKey(String privateKey, String s) {
		if (StringUtils.isBlank(s))
			return "";
		try {
			String mkey = "Rc";
			SecurityService service = (SecurityService) D.get(mkey);
			return mkey + service.encodePriKey(privateKey, s);
		} catch (Exception e) {
			e.printStackTrace();

			throw new CapecException("sec出错");
		}
	}

	public String decodePubKey(String publicKey, String s) {
		if (StringUtils.isBlank(s))
			return "";
		try {
			String fis = s.substring(0, 2);
			SecurityService service = (SecurityService) D.get(fis);
			if (service == null) {
				this.log.error("解密:" + s + "字符串时出错，未了现对应的解密方法，返回null");
				return null;
			}
			return service.decodePubKey(publicKey, s.substring(2, s.length()));
		} catch (Exception e) {
			e.printStackTrace();

			throw new CapecException("de出错");
		}
	}

	public static void main(String[] args) {
		String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDeLmXAwM1z+dRUL91ZO1BcVUaLDlEKZ5RskWWzqqnP8bxbH9ykmNzWBNMo0dRpKdcp6xSpiv784FmnfTAFk8r4VWKuMv72SPEZR754H0YlL6tdJGNcucJZyYJyS0iGXd+zmJXIljRzXfc2sFF0kUWGa/QmDQ+l+VPUIzSkrXpUQIDAQAB";
		String privatekey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMN4uZcDAzXP51FQv3Vk7UFxVRosOUQpnlGyRZbOqqc/xvFsf3KSY3NYE0yjR1Gkp1ynrFKmK/vzgWad9MAWTyvhVYq4y/vZI8RlHvngfRiUvq10kY1y5wlnJgnJLSIZd37OYlciWNHNd9zawUXSRRYZr9CYND6X5U9QjNKStelRAgMBAAECgYBE7+m8GWLjcowpAGpVc5+9CcQys9AKewcL1ux18DL9Qx/dex9Df981CaXczGTVpVUk9zlt+I7gj5NeZmyv5e5LdJsy4yt04DC3yRaOdWVdd36jyllgxq521BQWo0hlai8nr12zrzJwcFTm5bWcJElmGZ7Po0jwWFagzNi9U+qZQQJBAOUYpYWhd4eZE38LBfO45Pxi+4HYAt9mXdBDhdAQTWI4/7AntVMAjYKa5WpOOrtY+qDNcJGtqxf63F4wMiaQpPkCQQDabTbqJi+GQczNJzJnu/wSl4kiFX429bdKbZEmQhJkR0gCVdhkx/NGHmxcrE7XukWjbaGKALGAZHMm46+IsHUZAkBiU5G3KPYEXM7hV/nWASVGrbUEpZGWEHlzSYfFJToRTiVvlZtdZH9X7uNIHQtYHXh0S7hkzgUA5cpvIoBjzPEBAkEAvC1tKwqc1PCpTXFgdog4oZfVt/yGUTkdoMyGSUyQ5b2XZxOoKyMsDhO1zZ1RC3/Z8PNJQUhZo8BmiHGNz5geOQJBAJyK8/n++RcshS85221VNT5s1Nh6BKca1pOVXdtuLBXpnHjywB+bUmlm1qrU7SOXYwKU+rIOQ9zrDS7rmFhtxxw=";

		String s = "这是1k粮";
		SecurityControl c = getInstance();
		System.out.println(c.decodePriKey(privatekey, c.encodePubKey(publickey, s)));

		System.out.println(c.decodePubKey(publickey, c.encodePriKey(privatekey, s)));
	}
}
