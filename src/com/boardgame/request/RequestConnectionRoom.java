package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestConnectionRoom extends RequestBase {

	private Integer roomNo;
	private String password;
	private String nickName;
	
	public RequestConnectionRoom(Integer gameNo, String email, Integer roomNo, String nickName, String password) {
		super(Common.IDENTIFIER_CONNECT_ROOM, gameNo, email);
		this.roomNo = roomNo;
		this.password = password;
		this.nickName = nickName;
	}

	public Integer getRoomNo() {
		return roomNo;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getNickName() {
		return nickName;
	}
}
