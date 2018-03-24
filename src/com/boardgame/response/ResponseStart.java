package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseStart extends ResponseBase {
	public ResponseStart(int resCode) {
		super(Common.IDENTIFIER_START, resCode);		
	}
	
	public ResponseStart(int resCode, String message) {
		super(Common.IDENTIFIER_START, resCode, message);
	}
}
