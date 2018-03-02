package com.boardgame.response;

public class ResponseBase {
	String identifier;
	String resCode;
	String message;
	
	public ResponseBase(String identifier, String resCode) {
		this.identifier = identifier;
		this.resCode = resCode;
	}
	
	public ResponseBase(String identifier, String resCode, String message) {
		this.identifier = identifier;
		this.resCode = resCode;
		this.message = message;
	}
	
}
