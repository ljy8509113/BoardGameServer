package com.boardgame.davincicode.response;

import java.util.ArrayList;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.NumberCard;
import com.boardgame.davincicode.model.UserGameData;

public class ResponseSelectFieldCard extends ResponseBaseDavinci {
	int index;
	
	public ResponseSelectFieldCard(ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList, int turnUserIndex, boolean isSuccess, int roomNo, int index) {
		super(DavinciCommon.IDENTIFIER_SELECT_FIELD_CARD, userList, fieldCardList, turnUserIndex, roomNo);
		this.index = index;
	}
	
	public ResponseSelectFieldCard(int resCode, String message, ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList, int index) {
		super(DavinciCommon.IDENTIFIER_SELECT_FIELD_CARD, resCode, message, userList, fieldCardList);
		this.index = index;
	}

}
