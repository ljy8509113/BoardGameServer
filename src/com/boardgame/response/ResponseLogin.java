package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseLogin extends ResponseBase{
	private boolean isAutoLogin;
	private String email;
	private String password;
	
	public ResponseLogin(String resCode, boolean isAutoLogin, String email, String password) {
		super(Common.IDENTIFIER_LOGIN, resCode);
		this.isAutoLogin = isAutoLogin;
		this.password = password;
		this.email = email;
	}
	
	public ResponseLogin(String resCode, String msg) {
		super(Common.IDENTIFIER_LOGIN, resCode, msg);
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
