package com.boardgame.request.davincicode;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;

public class RequesetSelectNumber extends RequestBase{
	public int index;
	public Integer roomNo;
	public RequesetSelectNumber(String email, int roomNo, int index) {
		super(Common.IDENTIFIER_SELECT_NUMBER, Common.GAME_DAVINCICODE, email);
		this.roomNo = roomNo;
		this.index = index;		
	}

}
