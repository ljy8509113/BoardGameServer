package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseOutRoom extends ResponseBase {
	String email;
	
	public ResponseOutRoom(String email) {
		super(Common.IDENTIFIER_OUT_ROOM, ResCode.SUCCESS.getResCode());
		this.email = email;
	}
	
	public ResponseOutRoom(int resCode, String message) {
		super(Common.IDENTIFIER_OUT_ROOM, resCode, message);
	}
}
