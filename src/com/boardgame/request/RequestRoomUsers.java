package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestRoomUsers extends RequestBase{
	int roomNo;
	public RequestRoomUsers(String email, int roomNo) {
		super(Common.IDENTIFIER_ROOM_USERS, Common.GAME_DAVINCICODE, email);
		this.roomNo = roomNo;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	
	
}
