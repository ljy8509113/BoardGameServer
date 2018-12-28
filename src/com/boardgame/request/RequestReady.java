package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestReady extends RequestBase{
	boolean isReady;
	
	public RequestReady(Integer gameNo, int roomNo, String email, boolean isReady) {
		super(Common.IDENTIFIER_READY, gameNo, email, roomNo);
		this.isReady = isReady;
	}
	public boolean isReady() {
		return isReady;
	}
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
	
}
