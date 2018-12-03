package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestJoin extends RequestBase{
	private String nickName;
	
	public RequestJoin(Integer gameNo, String email, String nickName) {
		super(Common.IDENTIFIER_JOIN, gameNo, email);
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

}
