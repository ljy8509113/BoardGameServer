package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestCreateRoom extends RequestBase {
	private String title;
	private Integer maxUser;
	private String nickName;
	
	public RequestCreateRoom(Integer gameNo, String email, String title, Integer maxUser, String nickName) {
		super(Common.IDENTIFIER_CREATE_ROOM, gameNo, email);		
		this.title = title;
		this.maxUser = maxUser;
		this.nickName = nickName;
	}

	public String getTitle() {
		return title;
	}
	
	public Integer getMaxUser() {
		return maxUser;
	}

	public String getNickName() {
		return nickName;
	}
	
}
