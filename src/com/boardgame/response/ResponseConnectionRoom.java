package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseConnectionRoom extends ResponseBase {
	 
	public ResponseConnectionRoom(String resCode) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode);		
	}
	
	public ResponseConnectionRoom(String resCode, String message) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode, message);
	}
	
}
