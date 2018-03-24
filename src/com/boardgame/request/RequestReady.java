package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestReady extends RequestBase{
	boolean isReady;
	int roomNo;
	public RequestReady(Integer gameNo, int roomNo, String email, boolean isReady) {
		super(Common.IDENTIFIER_READY, gameNo, email);
		this.isReady = isReady;
		this.roomNo = roomNo;
	}
	public boolean isReady() {
		return isReady;
	}
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}	
	
}
