package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestLogin extends RequestBase {
	public RequestLogin(Integer gameNo, String email) {
		super(Common.IDENTIFIER_LOGIN, gameNo, email);
	}
}
