package com.boardgame.request.davincicode;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;

public class RequestNextTurn extends RequestBase {
	int currentNo;
	public RequestNextTurn(String email, int currentNo) {
		super(Common.IDENTIFIER_TURN, Common.GAME_DAVINCICODE, email);
		this.currentNo = currentNo;
	}
}
