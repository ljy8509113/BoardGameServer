package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestRoomPassword extends RequestBase {
	int roomNo;
	String password;
	public RequestRoomPassword(int roomNo, String email, String password) {
		super(Common.IDENTIFIER_ROOM_PASSWORD, Common.GAME_DAVINCICODE, email);
		this.password = password;
		this.roomNo = roomNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	
}
