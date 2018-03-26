package com.boardgame.request.davincicode;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;

public class RequestOpenCard extends RequestBase{
	int index;
	int number;
	
	public RequestOpenCard(String email, int index, int number) {
		super(Common.IDENTIFIER_OPEN_CARD, Common.GAME_DAVINCICODE, email);
		this.index = index;
		this.number = number;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
