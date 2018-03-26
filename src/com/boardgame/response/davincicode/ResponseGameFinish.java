package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseGameFinish extends ResponseBase{
	String winner;
	public ResponseGameFinish(String winner) {
		super(Common.IDENTIFIER_GAME_FINISH, ResCode.SUCCESS.getResCode());
		this.winner = winner;
	}
}
