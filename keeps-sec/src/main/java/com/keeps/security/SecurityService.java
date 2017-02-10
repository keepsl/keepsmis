package com.keeps.security;

public abstract interface SecurityService {

	public abstract String encode(String paramString);

	public abstract String decode(String paramString);

	public abstract String encodePubKey(String paramString1, String paramString2);

	public abstract String encodePriKey(String paramString1, String paramString2);

	public abstract String decodePubKey(String paramString1, String paramString2);

	public abstract String decodePriKey(String paramString1, String paramString2);
}
