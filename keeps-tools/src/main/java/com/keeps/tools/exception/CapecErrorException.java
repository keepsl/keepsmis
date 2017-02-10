package com.keeps.tools.exception;

@SuppressWarnings("serial")
public class CapecErrorException extends RuntimeException {
	public CapecErrorException(String message) {
		super(message);
	}

	public CapecErrorException(Exception e) {
		super(e.getMessage());
	}
}
