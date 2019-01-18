package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestConnectionRoom extends RequestBase {
	
	private String nickName;
	boolean isComputer;
	
	public RequestConnectionRoom(Integer gameNo, String email, Integer roomNo, String nickName, boolean isComputer) {
		super(Common.IDENTIFIER_CONNECT_ROOM, gameNo, email, roomNo);
		this.nickName = nickName;
		this.isComputer = isComputer;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public boolean isComputer() {
		return this.isComputer;
	}
}
