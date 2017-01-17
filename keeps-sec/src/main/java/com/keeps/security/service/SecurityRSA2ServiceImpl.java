package com.keeps.security.service;

public class SecurityRSA2ServiceImpl extends AbstractRSAService {
	private String pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDXfGes2Wdo1HsAv3QEFmTvclcW1PQcCxYYROdKd2KlUCB2a0SGQh6xMRfYiUN/tmI/5DH/0GA98UiHbubfqQTEfZJqGI7RIoSlfhmEq8MwmuHydx1DX1EHxGOR8E4nds+4/nAcO+cNs1WWwQ2SINykimBMmQV3HNY9vZ8seZAixwIDAQAB";

	private String prikey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANd8Z6zZZ2jUewC/dAQWZO9yVxbU9BwLFhhE50p3YqVQIHZrRIZCHrExF9iJQ3+2Yj/kMf/QYD3xSIdu5t+pBMR9kmoYjtEihKV+GYSrwzCa4fJ3HUNfUQfEY5HwTid2z7j+cBw75w2zVZbBDZIg3KSKYEyZBXcc1j29nyx5kCLHAgMBAAECgYBhmDSUcnqFXga/lfaDi2j3kb+j1ZshwFunbj2a2mtRF/M77uVAMV4ZPnTgW3q4su/YpA+JHuUX6NnCjRX/oltHlYe5rIkjpiwy/nxfLsNlvKQovfBwVkd8i/Ze/FJZxCJG85ENjD0PYe/ni1/6xVJK9OTLgg7tZyEFDHUsBnw5sQJBAP0Sc8i+iuqIDvw1U6TbI7v/yY4D2wsOvPIIDSjgqOmP1krpMPtR1dwPFhmAnUeKoqJpRlTTOVye16IE7+6Q0g0CQQDZ+qFbDaET61QObbmHonG2AMQI2G0eSWjBHgiB72ZP4BteBWrRiuORhkdJ8Q3Zd2botXSjfv0rDmUuTI7KpVcjAkB4VR/M4h03q35Emi6tgq2ZJOT3rAmpVlatVvq65ygp9oTUldD+j0j7HAlMEwW2EMuIIjqY9t6dlcG+fk5/x0i5AkEAhwA3oNnvEhQkag9A4aUVAeC1iLjp0+EpNLmyES9A0WV97ZwzTPXU/VMJodzec/oETUncpEnI6DqxJZyarxz5uwJBAOOeQsd3aV9EPkdFp4VBHEGrkhmX23xJTN20bsqF2rQtLbhsPXdD6VIRMyXMaIkkfAShsC1VHs3xn4XDth+E9ZA=";

	public static void main(String[] args) {
		SecurityRSA2ServiceImpl s = new SecurityRSA2ServiceImpl();
		String t = s.encode("真的是白痴");
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
