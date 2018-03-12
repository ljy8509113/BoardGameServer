package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestLogin extends RequestBase {
	private boolean isAutoLogin;
	private String email;
	private String password;
	
	public RequestLogin(Integer gameNo, String uuid) {
		super(Common.IDENTIFIER_LOGIN, gameNo, uuid);
	}

	public boolean isAutoLogin() {
		return isAutoLogin;
	}

	public void setAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
