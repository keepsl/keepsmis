package com.keeps.tools.exception;

@SuppressWarnings("serial")
public class CapecException extends RuntimeException {
	public CapecException(String message) {
		super(message);
	}

	public CapecException(Exception e) {
		super(e.getMessage());
	}
}
