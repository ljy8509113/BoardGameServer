package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;

public class ResponseSelectFieldCard extends ResponseBaseDavinci {
	public boolean isSuccess;
	public String selectUser;
	public ResponseSelectFieldCard(GameCardInfo cardInfo, String turnUser, boolean isSuccess, String selectUser) {
		super(Common.IDENTIFIER_SELECT_FIELD_CARD, cardInfo, turnUser);
		this.isSuccess = isSuccess;
		this.selectUser = selectUser;
	}

}
