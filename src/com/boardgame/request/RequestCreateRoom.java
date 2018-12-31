package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestCreateRoom extends RequestBase {
	private String title;
	private String password;
	private String nickName;
	
	public RequestCreateRoom(Integer gameNo, String email, String title, String nickName, String password) {
		super(Common.IDENTIFIER_CREATE_ROOM, gameNo, email);		
		this.title = title;
		this.password = password;
		this.nickName = nickName;
	}

	public String getTitle() {
		return title;
	}

	public String getPassword() {
		return password;
	}
	
	public String getNickName() {
		return nickName;
	}
	
}
