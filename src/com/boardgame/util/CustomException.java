package com.boardgame.util;

public class CustomException extends Exception {
	
	private static final long serialVersionUID = 1L;
	String resCode = "";
	String message = "";
	
	public CustomException(String resCode, String message) {
		super(message);
		this.resCode = resCode;
		this.message = message;
	}

	public String getResCode() {
		return resCode;
	}

	public String getMessage() {
		return message;
	}	
}
