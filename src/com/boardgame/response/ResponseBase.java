package com.boardgame.response;

public class ResponseBase {
	private String identifier;
	private String resCode;
	private String message;
	
	public ResponseBase(String identifier, String resCode) {
		this.identifier = identifier;
		this.resCode = resCode;
	}
	
	public ResponseBase(String identifier, String resCode, String message) {
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

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
