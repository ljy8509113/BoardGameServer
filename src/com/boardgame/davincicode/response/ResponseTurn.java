package com.boardgame.davincicode.response;

import java.util.ArrayList;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.NumberCard;
import com.boardgame.davincicode.model.UserGameData;

public class ResponseTurn extends ResponseBaseDavinci {
	
	public ResponseTurn(ArrayList<UserGameData> userList, ArrayList<NumberCard> fieldCardList, int turnUserIndex, int roomNo) {
		super(DavinciCommon.IDENTIFIER_TURN,userList, fieldCardList, turnUserIndex, roomNo);
	}
}
