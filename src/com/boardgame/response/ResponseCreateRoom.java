package com.boardgame.response;

public class ResponseCreateRoom extends ResponseBase{
	String title;
	
	public ResponseCreateRoom(String identifier, String resCode, String title) {
		super(identifier, resCode);
		this.title = title;
	}

}
