package com.boardgame.davincicode.request;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.request.RequestBase;

public class RequesetGameSituation extends RequestBase {

	public RequesetGameSituation(String email) {
		super(DavinciCommon.IDENTIFIER_GAME_CARD_INFO, DavinciCommon.GAME_DAVINCICODE, email);		
	}

}
