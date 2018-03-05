package com.boardgame.request;

public class RequestCreateRoom extends RequestBase {
	public String title;
	public Integer fullUser;
	
	public RequestCreateRoom(String identifier, Integer gameNo, String uuid, String title, Integer fullUser) {
		super(identifier, gameNo, uuid);		
		this.title = title;
		this.fullUser = fullUser;
	}

}
