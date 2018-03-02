package com.boardgame.request;

public class RequestUserInfo extends RequestBase {
	
	public RequestUserInfo(String identifier, Integer gameNo, String uuid) {
		super(identifier, gameNo, uuid);
	}
}
