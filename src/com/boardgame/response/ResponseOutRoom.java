package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseOutRoom extends ResponseBase {
	
	public ResponseOutRoom() {
		super(Common.IDENTIFIER_OUT_ROOM, ResCode.SUCCESS.getResCode());		
	}
	
	public ResponseOutRoom(int resCode, String message) {
		super(Common.IDENTIFIER_OUT_ROOM, resCode, message);
	}
}
