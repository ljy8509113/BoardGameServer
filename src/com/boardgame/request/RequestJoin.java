package com.boardgame.request;

import java.util.Date;

import com.boardgame.common.Common;

public class RequestJoin extends RequestBase{
	private String nickName;
	
	public RequestJoin(Integer gameNo, String email, String password, String nickName, Date birthday) {
		super(Common.IDENTIFIER_JOIN, gameNo, email);
		this.password = password;
		this.birthday = birthday;
		this.nickName = nickName;
	}
	
	public String getPassword() {
		return password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getNickName() {
		return nickName;
	}

}
