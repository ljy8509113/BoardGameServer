package com.boardgame.request.davincicode;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;

public class RequesetSelectNumber extends RequestBase{
	int number;
	public RequesetSelectNumber(String email, int number) {
		super(Common.IDENTIFIER_SELECT_NUMBER, Common.GAME_DAVINCICODE, email);
		this.number = number;
	}

}
