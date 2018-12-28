package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestRoomPassword extends RequestBase {
	String password;
	public RequestRoomPassword(int gameNo, int roomNo, String email, String password) {
		super(Common.IDENTIFIER_ROOM_PASSWORD, gameNo, email, roomNo);
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
