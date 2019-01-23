package com.boardgame.davincicode.response;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.GameCardInfo;

public class ResponseSelectFieldCard extends ResponseBaseDavinci {
	public ResponseSelectFieldCard(GameCardInfo cardInfo, String turnUser, boolean isSuccess, int roomNo) {
		super(DavinciCommon.IDENTIFIER_SELECT_FIELD_CARD, cardInfo, turnUser, roomNo);
	}
	
	public ResponseSelectFieldCard(int resCode, String message) {
		super(DavinciCommon.IDENTIFIER_SELECT_FIELD_CARD, resCode, message);
	}

}
