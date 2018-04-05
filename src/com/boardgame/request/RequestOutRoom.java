package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestOutRoom extends RequestBase{
	int roomNo;
	String outUser;
	
	public RequestOutRoom(Integer gameNo, String email, int roomNo, String outUser) {
		super(Common.IDENTIFIER_OUT_ROOM, gameNo, email);
		this.roomNo = roomNo;
		this.outUser = outUser;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	public String getOutUser() {
		return outUser;
	}
	public void setOutUser(String outUser) {
		this.outUser = outUser;
	}	
}
