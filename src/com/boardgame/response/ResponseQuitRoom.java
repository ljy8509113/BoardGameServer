package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseQuitRoom extends ResponseBase {
	String email;
	public ResponseQuitRoom(String email, int resCode) {
		super(Common.IDENTIFIER_QUTY_ROOM, resCode);
		this.email = email;
	}
	
	public ResponseQuitRoom(int resCode, String message) {
		super(Common.IDENTIFIER_QUTY_ROOM, resCode, message);
	}
}
