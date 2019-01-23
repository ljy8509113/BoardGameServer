package com.boardgame.davincicode.request;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.request.RequestBase;

public class RequestSelectFieldCard extends RequestBase{
	public int index;
	public RequestSelectFieldCard(int index, Integer gameNo, String email) {
		super(DavinciCommon.IDENTIFIER_SELECT_FIELD_CARD, gameNo, email);
		this.index = index;
	}

}
