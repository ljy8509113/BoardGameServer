package com.boardgame.response.davincicode;

import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseBaseDavinci extends ResponseBase {
	public GameCardInfo cardInfo;
	
	public ResponseBaseDavinci(String identifier, GameCardInfo cardInfo) {
		super(identifier, ResCode.SUCCESS.getResCode());
		this.cardInfo = cardInfo;
	}
	
	public ResponseBaseDavinci(String identifier, int resCode, String message) {
		super(identifier, resCode, message);
	}
	
	public ResponseBaseDavinci(String identifier, int resCode, String message, GameCardInfo cardInfo) {
		super(identifier, resCode, message);
		this.cardInfo = cardInfo;		
	}
}
