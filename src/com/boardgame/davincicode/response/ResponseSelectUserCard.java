package com.boardgame.davincicode.response;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.GameCardInfo;

public class ResponseSelectUserCard extends ResponseBaseDavinci {
	public boolean isSuccess;
	public String selectUser;
	public ResponseSelectUserCard(GameCardInfo cardInfo, String turnUser, boolean isSuccess, String selectUser, int roomNo ) {
		super(DavinciCommon.IDENTIFIER_SELECT_USER_CARD, cardInfo, turnUser, roomNo);
		this.isSuccess = isSuccess;
		this.selectUser = selectUser;
	}

}
