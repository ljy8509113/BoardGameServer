package com.boardgame.util;

public class CustomException extends Exception {
	
	private static final long serialVersionUID = 1L;
	int resCode = -1;
	String message = "";
	
	public CustomException(int resCode, String message) {
		super(message);
		this.resCode = resCode;
		this.message = message;
	}

	public int getResCode() {
		return resCode;
	}

	public String getMessage() {
		return message;
	}	
}
