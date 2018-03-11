package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestLogin extends RequestBase {
	public boolean isAutoLogin;
	public String email;
	public String password;
	
	public RequestLogin(Integer gameNo, String uuid) {
		super(Common.IDENTIFIER_LOGIN, gameNo, uuid);
	}

}
