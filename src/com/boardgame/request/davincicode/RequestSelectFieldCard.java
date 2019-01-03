package com.boardgame.request.davincicode;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;

public class RequestSelectFieldCard extends RequestBase{
	public int index;
	public RequestSelectFieldCard(int index, Integer gameNo, String email) {
		super(Common.IDENTIFIER_SELECT_FIELD_CARD, gameNo, email);
		this.index = index;
	}

}
