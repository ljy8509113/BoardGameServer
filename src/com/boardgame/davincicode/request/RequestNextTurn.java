package com.boardgame.davincicode.request;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.request.RequestBase;

public class RequestNextTurn extends RequestBase {
	int currentNo;
	public RequestNextTurn(String email, int currentNo) {
		super(DavinciCommon.IDENTIFIER_TURN, DavinciCommon.GAME_DAVINCICODE, email);
		this.currentNo = currentNo;
	}
}
