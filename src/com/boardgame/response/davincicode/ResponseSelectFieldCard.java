package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;

public class ResponseSelectFieldCard extends ResponseBaseDavinci {
	public ResponseSelectFieldCard(GameCardInfo cardInfo, String turnUser, boolean isSuccess) {
		super(Common.IDENTIFIER_SELECT_FIELD_CARD, cardInfo, turnUser);
	}
	
	public ResponseSelectFieldCard(int resCode, String message) {
		super(Common.IDENTIFIER_SELECT_FIELD_CARD, resCode, message);
	}

}
