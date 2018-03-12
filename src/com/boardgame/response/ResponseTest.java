package com.boardgame.response;

public class ResponseTest extends ResponseBase {
	 
	public ResponseTest(String identifier, int resCode) {
		super(identifier, resCode);		
	}
	
	public ResponseTest(String identifier, int resCode, String message) {
		super(identifier, resCode, message);
	}
	
}
