package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestOutRoom extends RequestBase{
	int roomNo;
	public RequestOutRoom(Integer gameNo, String email, int roomNo) {
		super(Common.IDENTIFIER_OUT_ROOM, gameNo, email);
		this.roomNo = roomNo;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	
}
