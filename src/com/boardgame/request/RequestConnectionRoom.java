package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestConnectionRoom extends RequestBase {

	private Integer roomNo;
	private String nickName;
	private String password;
	
	public RequestConnectionRoom(Integer gameNo, String email, Integer roomNo, String nickName, String password) {
		super(Common.IDENTIFIER_CONNECT_ROOM, gameNo, email);
		this.roomNo = roomNo;
		this.nickName = nickName;
		this.password = password;
	}

	public Integer getRoomNo() {
		return roomNo;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public String getPassword() {
		return password;
	}
}
