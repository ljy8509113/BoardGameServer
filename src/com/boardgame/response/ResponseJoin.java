package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseJoin extends ResponseBase {

	public ResponseJoin() {
		super(Common.IDENTIFIER_JOIN, ResCode.SUCCESS.getResCode());
	}
	
	public ResponseJoin(int resCode, String msg) {
		super(Common.IDENTIFIER_JOIN, resCode, msg);
	}

}
