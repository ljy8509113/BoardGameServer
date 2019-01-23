package com.boardgame.davincicode.request;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.request.RequestBase;

public class RequestSelectUserCard extends RequestBase{
	public int index;
	public String selectUser;
	public RequestSelectUserCard(int index, String selectUser, Integer gameNo, String email) {
		super(DavinciCommon.IDENTIFIER_SELECT_USER_CARD, gameNo, email);
		this.index = index;
		this.selectUser = selectUser;
	}

}
