package com.boardgame.davincicode.response;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.GameCardInfo;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseGameCardInfo extends ResponseBase{
	GameCardInfo cardInfo;
	public ResponseGameCardInfo(GameCardInfo cardInfo) {
		super(DavinciCommon.IDENTIFIER_GAME_CARD_INFO, ResCode.SUCCESS.getResCode());
		this.cardInfo = cardInfo;
	}
	
	public ResponseGameCardInfo(int resCode, String message) {
		super(DavinciCommon.IDENTIFIER_GAME_CARD_INFO, resCode, message);
	}
	
	public ResponseGameCardInfo(int resCode, String message, GameCardInfo cardInfo) {
		super(DavinciCommon.IDENTIFIER_GAME_CARD_INFO, resCode, message);
		this.cardInfo = cardInfo;		
	}
}
