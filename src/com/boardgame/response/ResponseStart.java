package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseStart extends ResponseBase {
	public ResponseStart() {
		super(Common.IDENTIFIER_START, ResCode.SUCCESS.getResCode());		
	}
	
	public ResponseStart(int resCode, String message) {
		super(Common.IDENTIFIER_START, resCode, message);
	}
}
