package com.boardgame.response;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.response.davincicode.ResponseBaseDavinci;

public class ResponseStart extends ResponseBaseDavinci {
	public ResponseStart(GameCardInfo info) {
		super(Common.IDENTIFIER_START, info);
	}
	
	public ResponseStart(int resCode, String message) {
		super(Common.IDENTIFIER_START, resCode, message);
	}
}
