package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestConnectionRoom extends RequestBase {

	private Integer roomId;
	private String nickName;
	private String password;
	
	public RequestConnectionRoom(Integer gameNo, String email, Integer roomId, String nickName, String password) {
		super(Common.IDENTIFIER_CONNECT_ROOM, gameNo, email);
		this.roomId = roomId;
		this.nickName = nickName;
		this.password = password;
	}

	public Integer getRoomId() {
		return roomId;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public String getPassword() {
		return password;
	}
}
