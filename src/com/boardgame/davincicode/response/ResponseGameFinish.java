package com.boardgame.davincicode.response;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseGameFinish extends ResponseBase{
	String winner;
	public ResponseGameFinish(String winner) {
		super(DavinciCommon.IDENTIFIER_GAME_FINISH, ResCode.SUCCESS.getResCode());
		this.winner = winner;
	}
}
