package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;

public class ResponseInit extends ResponseBaseDavinci{
	public ResponseInit(GameCardInfo info) {
		super(Common.IDENTIFIER_INIT_GAME, info, null);
	}
	
	public ResponseInit(int resCode, String message) {
		super(Common.IDENTIFIER_INIT_GAME, resCode, message);
	}
}
