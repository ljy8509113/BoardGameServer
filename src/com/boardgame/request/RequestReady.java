package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestReady extends RequestBase{
	public RequestReady(Integer gameNo, String email) {
		super(Common.IDENTIFIER_READY, gameNo, email);		
	}	
}
