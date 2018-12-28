package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestRoomInfo extends RequestBase{
	
	public RequestRoomInfo(String email, int gameNo, int roomNo) {
		super(Common.IDENTIFIER_ROOM_INFO, gameNo, email, roomNo);
	}
}
