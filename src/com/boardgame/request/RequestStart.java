package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestStart extends RequestBase{
	
	public RequestStart(Integer gameNo, String email, int roomNo) {
		super(Common.IDENTIFIER_START, gameNo, email, roomNo);		
	}
}
