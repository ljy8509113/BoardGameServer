package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseLogin extends ResponseBase{
	private boolean isAutoLogin;
	private String email;
	private String password;
	private String nickName;
	
	public ResponseLogin(String resCode, boolean isAutoLogin, String email, String password, String nickName) {
		super(Common.IDENTIFIER_LOGIN, resCode);
		this.isAutoLogin = isAutoLogin;
		this.password = password;
		this.email = email;
		this.nickName = nickName;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
