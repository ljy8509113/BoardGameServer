package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.model.User;

public class ResponseConnectionRoom extends ResponseBase {
	private User masterUser;
	
	public ResponseConnectionRoom(int resCode) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode);		
	}
	
	public ResponseConnectionRoom(int resCode, String message) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode, message);
	}
	
}
