package com.boardgame.response;

public class ResponseTest extends ResponseBase {
	 
	public ResponseTest(String identifier, String resCode) {
		super(identifier, resCode);		
	}
	
	public ResponseTest(String identifier, String resCode, String message) {
		super(identifier, resCode, message);
	}
	
}
