package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestConnectionRoom extends RequestBase {

	private Integer roomId;
	private String nickName;
	
	public RequestConnectionRoom(Integer gameNo, String email, Integer roomId, String nickName) {
		super(Common.IDENTIFIER_CONNECT_ROOM, gameNo, email);
		this.roomId = roomId;
		this.nickName = nickName;
	}

	public Integer getRoomId() {
		return roomId;
	}
	
	public String getNickName() {
		return nickName;
	}
}
