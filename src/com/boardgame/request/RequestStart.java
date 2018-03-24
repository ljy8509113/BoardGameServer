package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestStart extends RequestBase{
	int roomNo;
	public RequestStart(Integer gameNo, String email, int roomNo) {
		super(Common.IDENTIFIER_START, gameNo, email);		
		this.roomNo = roomNo;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
}
