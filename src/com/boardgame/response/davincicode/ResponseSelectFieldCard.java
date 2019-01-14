package com.boardgame.response.davincicode;

import com.boardgame.model.davincicode.GameCardInfo;

public class ResponseSelectFieldCard extends ResponseBaseDavinci {
	public boolean isSuccess;
	public ResponseSelectFieldCard(String identifier, GameCardInfo cardInfo, String turnUser, boolean isSuccess) {
		super(identifier, cardInfo, turnUser);
		this.isSuccess = isSuccess;
	}

}
