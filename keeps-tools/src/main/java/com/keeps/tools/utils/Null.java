package com.keeps.tools.utils;

public class Null {
	private static Null INSTANCE = new Null();

	public static Null getInstance() {
		return INSTANCE;
	}

	public boolean equals(Object obj) {
		return (obj == null);
	}

	public int hashCode() {
		return 26;
	}

	public String toString() {
		return "[This is " + super.getClass().getCanonicalName() + "]";
	}
}
