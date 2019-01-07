package com.boardgame.request;

public class RequestBase {
	private String identifier;
	private int gameNo;
	private String email;
	private int roomNo;
	
	public RequestBase(String identifier, int gameNo, String email) {
		this.identifier = identifier;
		this.gameNo = gameNo;
		this.email = email;
		roomNo = -1;
	}

	public RequestBase(String identifier, int gameNo, String email, int roomNo) {
		this.identifier = identifier;
		this.gameNo = gameNo;
		this.email = email;
		this.roomNo = roomNo;
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
	
	public int getRoomNo() {
		return roomNo;
	}
}
