package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestLogin extends RequestBase {
	public boolean isAutoId;
	public RequestLogin(Integer gameNo, String email, boolean isAutoId) {
		super(Common.IDENTIFIER_LOGIN, gameNo, email);
		this.isAutoId = isAutoId;
	}
}
