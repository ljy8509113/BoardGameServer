package com.boardgame.request;

public class RequestBase {
	private String identifier;
	private Integer gameNo;
	private String email;
	private String nickName;
	
	public RequestBase(String identifier, Integer gameNo, String email, String nickName) {
		this.identifier = identifier;
		this.gameNo = gameNo;
		this.email = email;		
		this.nickName = nickName;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Integer getGameNo() {
		return gameNo;
	}

	public String getEmail() {
		return email;
	}
	
	public String getNickName() {
		return nickName;
	}
	
}
