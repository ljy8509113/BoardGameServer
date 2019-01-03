package com.boardgame.request.davincicode;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;

public class RequestSelectUserCard extends RequestBase{
	public int index;
	public String selectUser;
	public RequestSelectUserCard(int index, String selectUser, Integer gameNo, String email) {
		super(Common.IDENTIFIER_SELECT_USER_CARD, gameNo, email);
		this.index = index;
		this.selectUser = selectUser;
	}

}
