package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;

public class ResponseSelectUserCard extends ResponseBaseDavinci {
	public boolean isSuccess;
	public String selectUser;
	public ResponseSelectUserCard(GameCardInfo cardInfo, String turnUser, boolean isSuccess, String selectUser ) {
		super(Common.IDENTIFIER_SELECT_USER_CARD, cardInfo, turnUser);
		this.isSuccess = isSuccess;
		this.selectUser = selectUser;
	}

}
