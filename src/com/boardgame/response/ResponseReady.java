package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseReady extends ResponseBase {
	String email;
	public ResponseReady(String email, int resCode) {
		super(Common.IDENTIFIER_READY, resCode);
		this.email = email;
	}
	
	public ResponseReady(int resCode, String message) {
		super(Common.IDENTIFIER_READY, resCode, message);
	}
}
