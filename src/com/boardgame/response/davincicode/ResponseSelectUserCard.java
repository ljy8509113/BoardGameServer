package com.boardgame.response.davincicode;

import com.boardgame.model.davincicode.GameCardInfo;

public class ResponseSelectUserCard extends ResponseBaseDavinci {
	public boolean isSuccess;
	public ResponseSelectUserCard(String identifier, GameCardInfo cardInfo, String turnUser, boolean isSuccess ) {
		super(identifier, cardInfo, turnUser);
		this.isSuccess = isSuccess;
	}

}
