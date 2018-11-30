package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestCreateRoom extends RequestBase {
	private String title;
	private Integer maxUser;
	private String password;
	
	public RequestCreateRoom(Integer gameNo, String email, String title, Integer maxUser, String nickName, String password) {
		super(Common.IDENTIFIER_CREATE_ROOM, gameNo, email, nickName);		
		this.title = title;
		this.maxUser = maxUser;
		this.password = password;
	}

	public String getTitle() {
		return title;
	}
	
	public Integer getMaxUser() {
		return maxUser;
	}

	public String getPassword() {
		return password;
	}
	
	
}
