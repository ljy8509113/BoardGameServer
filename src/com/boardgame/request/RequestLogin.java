package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestLogin extends RequestBase {
	public String password;
	
	public RequestLogin(Integer gameNo, String email, String password) {
		super(Common.IDENTIFIER_LOGIN, gameNo, email);
		this.password = password;
		
	}
}
