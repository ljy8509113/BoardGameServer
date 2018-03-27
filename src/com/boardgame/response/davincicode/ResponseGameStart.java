package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseGameStart extends ResponseBase {
	GameCardInfo cardInfo;
	public ResponseGameStart(GameCardInfo cardInfo) {
		super(Common.IDENTIFIER_GAME_START, ResCode.SUCCESS.getResCode());
		this.cardInfo = cardInfo;
	}
}
