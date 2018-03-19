package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestCreateRoom extends RequestBase {
	private String title;
	private Integer maxUser;
	private String nickName;
	private String password;
	
	public RequestCreateRoom(Integer gameNo, String email, String title, Integer maxUser, String nickName, String password) {
		super(Common.IDENTIFIER_CREATE_ROOM, gameNo, email);		
		this.title = title;
		this.maxUser = maxUser;
		this.nickName = nickName;
		this.password = password;
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

	public String getPassword() {
		return password;
	}
	
	
}
