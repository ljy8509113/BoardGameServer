package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseInitGame extends ResponseBase {
	GameCardInfo cardInfo;
	public ResponseInitGame(GameCardInfo cardInfo) {
		super(Common.IDENTIFIER_INIT_GAME, ResCode.SUCCESS.getResCode());
		this.cardInfo = cardInfo;
	}
}
