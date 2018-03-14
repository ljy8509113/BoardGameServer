package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestGamingUser extends RequestBase {

	public RequestGamingUser(Integer gameNo, String email) {
		super(Common.IDENTIFIER_GAMING_USER, gameNo, email);		
	}

}
