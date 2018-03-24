package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestQuitRoom extends RequestBase{

	public RequestQuitRoom(Integer gameNo, String email) {
		super(Common.IDENTIFIER_QUTY_ROOM, gameNo, email);
		
	}

}
