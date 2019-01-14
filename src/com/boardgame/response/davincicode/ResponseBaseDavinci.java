package com.boardgame.response.davincicode;

import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseBaseDavinci extends ResponseBase {
	public GameCardInfo cardInfo;
	public String turnUser;
	
	public ResponseBaseDavinci(String identifier, GameCardInfo cardInfo, String turnUer) {
		super(identifier, ResCode.SUCCESS.getResCode());
		this.cardInfo = cardInfo;
		this.turnUser = turnUer;
	}
	
	public ResponseBaseDavinci(String identifier, int resCode, String message) {
		super(identifier, resCode, message);
	}
	
	public ResponseBaseDavinci(String identifier, int resCode, String message, GameCardInfo cardInfo) {
		super(identifier, resCode, message);
		this.cardInfo = cardInfo;		
	}
}
