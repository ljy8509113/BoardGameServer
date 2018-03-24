package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestStart extends RequestBase{

	public RequestStart(Integer gameNo, String email) {
		super(Common.IDENTIFIER_START, gameNo, email);		
	}

}
