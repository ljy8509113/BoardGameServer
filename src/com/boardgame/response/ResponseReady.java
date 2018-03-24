package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseReady extends ResponseBase {
	String email;
	boolean isReady;
	
	//성공 
	public ResponseReady(String email, boolean isReady) {
		super(Common.IDENTIFIER_READY, ResCode.SUCCESS.getResCode());
		this.email = email;
		this.isReady = isReady;
	}
	
	//실패
	public ResponseReady(int resCode, String message) {
		super(Common.IDENTIFIER_READY, resCode, message);
	}
}
