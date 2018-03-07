package com.boardgame.request;

public class RequestConnectionRoom extends RequestBase {

	private Integer roomId;
	public RequestConnectionRoom(String identifier, Integer gameNo, String uuid, Integer roomId) {
		super(identifier, gameNo, uuid);
		this.roomId = roomId;
	}

	public Integer getRoomId() {
		return roomId;
	}
}
