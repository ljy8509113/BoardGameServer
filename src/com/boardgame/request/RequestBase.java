package com.boardgame.request;

public class RequestBase {
	public String identifier;
	public Integer gameNo;
	public String uuid;
	
	public RequestBase(String identifier, Integer gameNo, String uuid) {
		this.identifier = identifier;
		this.gameNo = gameNo;
		this.uuid = uuid;		
	}
}
