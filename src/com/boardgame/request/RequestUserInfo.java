package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestUserInfo extends RequestBase {
	
	public RequestUserInfo(Integer gameNo, String email) {
		super(Common.IDENTIFIER_USER_INFO, gameNo, email);
	}
}
