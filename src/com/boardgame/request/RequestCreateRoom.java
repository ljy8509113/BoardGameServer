package com.boardgame.request;

public class RequestCreateRoom extends RequestBase {
	private String title;
	private Integer maxUser;
	
	public RequestCreateRoom(String identifier, Integer gameNo, String uuid, String title, Integer maxUser) {
		super(identifier, gameNo, uuid);		
		this.title = title;
		this.maxUser = maxUser;
	}

	public String getTitle() {
		return title;
	}
	
	public Integer getMaxUser() {
		return maxUser;
	}
	
}
