package com.boardgame.request.davincicode;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;

public class RequesetInitNumber extends RequestBase{
	int number;
	public RequesetInitNumber(String email, int number) {
		super(Common.IDENTIFIER_INIT_NUMBER, Common.GAME_DAVINCICODE, email);
		this.number = number;
	}

}
