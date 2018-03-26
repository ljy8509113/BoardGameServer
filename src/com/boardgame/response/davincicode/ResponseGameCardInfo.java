package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseGameCardInfo extends ResponseBase{
	GameCardInfo cardInfo;
	public ResponseGameCardInfo(GameCardInfo cardInfo) {
		super(Common.IDENTIFIER_GAME_SITUATION, ResCode.SUCCESS.getResCode());
		this.cardInfo = cardInfo;
	}
	
	public ResponseGameCardInfo(int resCode, String message) {
		super(Common.IDENTIFIER_GAME_SITUATION, resCode, message);
	}
	
	public ResponseGameCardInfo(int resCode, String message, GameCardInfo cardInfo) {
		super(Common.IDENTIFIER_GAME_SITUATION, resCode, message);
		this.cardInfo = cardInfo;		
	}
}
