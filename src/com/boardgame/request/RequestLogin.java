package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestLogin extends RequestBase {
	private boolean isAutoLogin;
	private String password;
	
	public RequestLogin(Integer gameNo, String email) {
		super(Common.IDENTIFIER_LOGIN, gameNo, email);
	}

	public boolean isAutoLogin() {
		return isAutoLogin;
	}

	public void setAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	public String getEmail() {
		return super.getEmail();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
