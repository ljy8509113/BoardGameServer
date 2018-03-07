package com.boardgame.request;

public class RequestBase {
	private String identifier;
	private Integer gameNo;
	private String uuid;
	
	public RequestBase(String identifier, Integer gameNo, String uuid) {
		this.identifier = identifier;
		this.gameNo = gameNo;
		this.uuid = uuid;		
	}

	public String getIdentifier() {
		return identifier;
	}

	public Integer getGameNo() {
		return gameNo;
	}

	public String getUuid() {
		return uuid;
	}
	
}
