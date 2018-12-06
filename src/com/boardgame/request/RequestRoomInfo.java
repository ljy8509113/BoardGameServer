package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestRoomInfo extends RequestBase{
	int roomNo;
	public RequestRoomInfo(String email, int roomNo) {
		super(Common.IDENTIFIER_ROOM_INFO, Common.GAME_DAVINCICODE, email);
		this.roomNo = roomNo;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
}
