package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseRoomPassword extends ResponseBase {
	public int roomNo;
	
	public ResponseRoomPassword(int roomNo) {
		super(Common.IDENTIFIER_ROOM_PASSWORD, ResCode.SUCCESS.getResCode());
		this.roomNo = roomNo;
	}
	
	public ResponseRoomPassword(int resCode, String message) {
		super(Common.IDENTIFIER_ROOM_PASSWORD, resCode, message);
	}
}
