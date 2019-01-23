package com.boardgame.davincicode.response;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.GameCardInfo;

public class ResponseStartDavincicode extends ResponseBaseDavinci {
	public ResponseStartDavincicode(GameCardInfo info, int roomNo) {
		super(DavinciCommon.IDENTIFIER_START, info, null, roomNo);
	}
	public ResponseStartDavincicode(int resCode, String message) {
		super(DavinciCommon.IDENTIFIER_START, resCode, message);
	}
}
