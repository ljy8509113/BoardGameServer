package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestJoin extends RequestBase{
	private String nickName;
	String password;
	
	public RequestJoin(int gameNo, String email, String nickName, String password) {
		super(Common.IDENTIFIER_JOIN, gameNo, email);
		this.nickName = nickName;
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}
	
	public String getPassword() {
		return this.password;
	}

}
