package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestCreateRoom extends RequestBase {
	private String title;
	private Integer maxUser;
	
	public RequestCreateRoom(Integer gameNo, String uuid, String title, Integer maxUser) {
		super(Common.IDENTIFIER_CREATE_ROOM, gameNo, uuid);		
		this.title = title;
		this.maxUser = maxUser;
	}

	public String getTitle() {
		return title;
	}
	
	public Integer getMaxUser() {
		return maxUser;
	}
	
}
