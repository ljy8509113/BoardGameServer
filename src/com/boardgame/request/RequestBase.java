package com.boardgame.request;

public class RequestBase {
	private String identifier;
	private Integer gameNo;
	private String email;
	
	public RequestBase(String identifier, Integer gameNo, String email) {
		this.identifier = identifier;
		this.gameNo = gameNo;
		this.email = email;		
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
	
}
