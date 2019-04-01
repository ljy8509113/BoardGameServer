package com.boardgame.davincicode.response;

import java.util.ArrayList;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.NumberCard;
import com.boardgame.davincicode.model.UserGameData;

public class ResponseGameCardInfo extends ResponseBaseDavinci{
	
	public ResponseGameCardInfo(ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList, int turnUserNo, int roomNo) {
		super(DavinciCommon.IDENTIFIER_GAME_CARD_INFO, userList, fieldCardList, turnUserNo, roomNo);
				
	}
	
	public ResponseGameCardInfo(int resCode, String message) {
		super(DavinciCommon.IDENTIFIER_GAME_CARD_INFO, resCode, message);
	}
	
	public ResponseGameCardInfo(int resCode, String message, ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList) {
		super(DavinciCommon.IDENTIFIER_GAME_CARD_INFO, resCode, message, userList, fieldCardList);
				
	}
}
