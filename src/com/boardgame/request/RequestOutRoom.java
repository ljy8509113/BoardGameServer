package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestOutRoom extends RequestBase{
	String outUser;
	
	public RequestOutRoom(Integer gameNo, String email, int roomNo, String outUser) {
		super(Common.IDENTIFIER_OUT_ROOM, gameNo, email, roomNo);
		this.outUser = outUser;
	}
	
	public String getOutUser() {
		return outUser;
	}
	public void setOutUser(String outUser) {
		this.outUser = outUser;
	}	
}
