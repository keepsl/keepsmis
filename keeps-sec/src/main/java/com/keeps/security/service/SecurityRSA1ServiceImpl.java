package com.keeps.security.service;

import com.keeps.security.SecurityService;

public class SecurityRSA1ServiceImpl extends AbstractRSAService {
	private String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBSD1joIwsxBSoYg2f+dn1HP97iO46Ec32mRpUsk5I34WFmSgd7il86K+lo/kcYUQLX0UXOlBDolmcAmIIgeCE624AeliM6QzcGfT5+WgYIs8KX9QhVe3hErfD6PI5QfvRJI1oIucgIb+1kNLrpxoum9Mjxy+my5/h/2xUix4oiwIDAQAB";

	private String prikey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIFIPWOgjCzEFKhiDZ/52fUc/3uI7joRzfaZGlSyTkjfhYWZKB3uKXzor6Wj+RxhRAtfRRc6UEOiWZwCYgiB4ITrbgB6WIzpDNwZ9Pn5aBgizwpf1CFV7eESt8Po8jlB+9EkjWgi5yAhv7WQ0uunGi6b0yPHL6bLn+H/bFSLHiiLAgMBAAECgYBoEE8h76yFqDv8ohjXidVw4SuhM8KWyZghhHHWdURgklO3C2ZoWTnaCq7ARTITLxQHl2NU5CKRnpBE7QIeWPTwwRYsl3jpNF/EMjWcLPl9MtqbBjKqET3xJp2i8gwMYfbMLtoVUo1iGALy4s4w2/K4f11taZGFN7yWx+gzDPaoqQJBANOwnUn+/CzG47Y9U5AXH6d2kkm6b/XgZSPjAZk/OFYTGBYF1kdnmIAjBudeWWwpx+XmR2UyfC7qNOlD6PcDll0CQQCcV9Chiz/8/Y8N/lgFbYOUuMsB1OrqVVK4X7sdvBr55hoFuG6KXFbH8vD9w0QTPEcKc6YKhHfx+DNhcwIO8HwHAkAiD6vvSRDoX4QqQYndiGr2dhSpuk7jgByl/pO2Rb7Z0q6wdHX6/UdDbt9ggQTga4SXkErylIH4xoSG9/9XJ4OdAkAc4mx9LAjqaL4hS7hfaFhDBdEbyYaNPK5HuggL+lDRkp2EKQrjLSQrVMsJVyYgl6ZYW/11O/wdDEoR2vmqYOvJAkEAnR6+S2RHwvXhfYsD7CnF/CpfLXk4KTMrFKcB2FWwFsr4yv8LIWPnsZhaHkGNlcp/TtXUxRUBftxsxiVNdSK2DQ==";

	public static void main(String[] args) {
		SecurityRSA1ServiceImpl s = new SecurityRSA1ServiceImpl();
		String t = s.encode("白痴");
		System.out.println(t);

		System.out.println(s.decode(t));
	}

	protected String getPubKey() {
		return this.pubkey;
	}

	protected String getPriKey() {
		return this.prikey;
	}
}
