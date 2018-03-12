package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseConnectionRoom extends ResponseBase {
	 
	public ResponseConnectionRoom(int resCode) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode);		
	}
	
	public ResponseConnectionRoom(int resCode, String message) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode, message);
	}
	
}
