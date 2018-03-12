package com.boardgame.response;

public class ResponseBase {
	private String identifier;
	private int resCode;
	private String message;
	
	public ResponseBase(String identifier, int resCode) {
		this.identifier = identifier;
		this.resCode = resCode;
	}
	
	public ResponseBase(String identifier, int resCode, String message) {
		this.identifier = identifier;
		this.resCode = resCode;
		this.message = message;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
