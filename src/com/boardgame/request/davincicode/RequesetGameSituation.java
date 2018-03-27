package com.boardgame.request.davincicode;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;

public class RequesetGameSituation extends RequestBase {

	public RequesetGameSituation(String email) {
		super(Common.IDENTIFIER_GAME_CARD_INFO, Common.GAME_DAVINCICODE, email);		
	}

}
