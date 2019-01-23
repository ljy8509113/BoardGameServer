package com.boardgame.davincicode.response;

import com.boardgame.davincicode.model.GameCardInfo;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseBaseDavinci extends ResponseBase {
	public GameCardInfo cardInfo;
	public String turnUser;
	public int roomNo;
	
	public ResponseBaseDavinci(String identifier, GameCardInfo cardInfo, String turnUer, int roomNo) {
		super(identifier, ResCode.SUCCESS.getResCode());
		this.cardInfo = cardInfo;
		this.turnUser = turnUer;
		this.roomNo = roomNo;
	}
	
	public ResponseBaseDavinci(String identifier, int resCode, String message) {
		super(identifier, resCode, message);
	}
	
	public ResponseBaseDavinci(String identifier, int resCode, String message, GameCardInfo cardInfo) {
		super(identifier, resCode, message);
		this.cardInfo = cardInfo;		
	}
}
