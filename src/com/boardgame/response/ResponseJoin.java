package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseJoin extends ResponseBase {

	public ResponseJoin(int resCode) {
		super(Common.IDENTIFIER_JOIN, resCode);
		
	}
	
	public ResponseJoin(int resCode, String msg) {
		super(Common.IDENTIFIER_JOIN, resCode, msg);
	}

}
