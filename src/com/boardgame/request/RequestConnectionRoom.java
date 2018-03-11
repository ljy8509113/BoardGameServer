package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestConnectionRoom extends RequestBase {

	private Integer roomId;
	public RequestConnectionRoom(Integer gameNo, String uuid, Integer roomId) {
		super(Common.IDENTIFIER_CONNECT_ROOM, gameNo, uuid);
		this.roomId = roomId;
	}

	public Integer getRoomId() {
		return roomId;
	}
}
