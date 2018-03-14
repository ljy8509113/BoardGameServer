package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestConnectionRoom extends RequestBase {

	private Integer roomId;
	public RequestConnectionRoom(Integer gameNo, String email, Integer roomId) {
		super(Common.IDENTIFIER_CONNECT_ROOM, gameNo, email);
		this.roomId = roomId;
	}

	public Integer getRoomId() {
		return roomId;
	}
}
