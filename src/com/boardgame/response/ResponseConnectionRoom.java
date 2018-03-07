package com.boardgame.response;

public class ResponseConnectionRoom extends ResponseBase {
	 
	public ResponseConnectionRoom(String identifier, String resCode) {
		super(identifier, resCode);		
	}
	
	public ResponseConnectionRoom(String identifier, String resCode, String message) {
		super(identifier, resCode, message);
	}
	
}
