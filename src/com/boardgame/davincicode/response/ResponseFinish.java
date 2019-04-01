package com.boardgame.davincicode.response;

import java.util.ArrayList;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.NumberCard;
import com.boardgame.davincicode.model.UserGameData;

public class ResponseFinish extends ResponseBaseDavinci {
	public ResponseFinish(ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList, int turnUserNo, int roomNo) {
		super(DavinciCommon.IDENTIFIER_GAME_FINISH, userList, fieldCardList, turnUserNo, roomNo);
	}
}
