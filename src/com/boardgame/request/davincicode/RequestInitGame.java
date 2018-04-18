package com.boardgame.request.davincicode;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;

public class RequestInitGame extends RequestBase{
	int roomNo;
	public RequestInitGame(String email, int roomNo) {
		super(Common.IDENTIFIER_INIT_GAME, Common.GAME_DAVINCICODE, email);
		this.roomNo = roomNo;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

}
