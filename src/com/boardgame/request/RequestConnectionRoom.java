package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestConnectionRoom extends RequestBase {

	private Integer roomNo;
	private String password;
	
	public RequestConnectionRoom(Integer gameNo, String email, Integer roomNo, String nickName, String password) {
		super(Common.IDENTIFIER_CONNECT_ROOM, gameNo, email, nickName);
		this.roomNo = roomNo;
		this.password = password;
	}

	public Integer getRoomNo() {
		return roomNo;
	}
	
	public String getPassword() {
		return password;
	}
}
