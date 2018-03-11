package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestRoomList extends RequestBase {

	private Integer current;
	private Integer count;
	
	public RequestRoomList(Integer gameNo, String uuid, Integer current, Integer count) {
		super(Common.IDENTIFIER_ROOM_LIST, gameNo, uuid);
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
