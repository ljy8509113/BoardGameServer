package com.boardgame.davincicode.response;

import java.util.ArrayList;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.NumberCard;
import com.boardgame.davincicode.model.UserGameData;

public class ResponseStartDavincicode extends ResponseBaseDavinci {
	public ResponseStartDavincicode(ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList, int roomNo) {
		super(DavinciCommon.IDENTIFIER_START, userList, fieldCardList, -1, roomNo);
	}
	public ResponseStartDavincicode(int resCode, String message) {
		super(DavinciCommon.IDENTIFIER_START, resCode, message);
	}
}
