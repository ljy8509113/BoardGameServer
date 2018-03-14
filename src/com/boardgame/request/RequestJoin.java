package com.boardgame.request;

import java.util.Date;

import com.boardgame.common.Common;

public class RequestJoin extends RequestBase{
	private String email;
	private String password;
	private Date birthday;
	private String nickName;
	
	public RequestJoin(Integer gameNo, String email, String password, String nickName, Date birthday) {
		super(Common.IDENTIFIER_JOIN, gameNo, email);
		this.email = email;
		this.password = password;
		this.birthday = birthday;
		this.nickName = nickName;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	

}
