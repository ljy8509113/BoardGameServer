package com.boardgame.request;

public class RequestCreateRoom extends RequestBase {
	private String title;
	private Integer fullUser;
	
	public RequestCreateRoom(String identifier, Integer gameNo, String uuid, String title, Integer fullUser) {
		super(identifier, gameNo, uuid);		
		this.title = title;
		this.fullUser = fullUser;
	}

	public String getTitle() {
		return title;
	}
	
	public Integer getFullUser() {
		return fullUser;
	}
	
}
