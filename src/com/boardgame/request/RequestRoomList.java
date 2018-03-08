package com.boardgame.request;

public class RequestRoomList extends RequestBase {

	private Integer current;
	private Integer count;
	
	public RequestRoomList(String identifier, Integer gameNo, String uuid, Integer current, Integer count) {
		super(identifier, gameNo, uuid);
		this.current = current;
		this.count = count;
	}

	public Integer getCurrent() {
		return current;
	}
	
	public Integer getCount() {
		return count;
	}
	
}
