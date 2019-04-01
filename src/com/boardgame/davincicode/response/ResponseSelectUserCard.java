package com.boardgame.davincicode.response;

import java.util.ArrayList;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.NumberCard;
import com.boardgame.davincicode.model.UserGameData;

public class ResponseSelectUserCard extends ResponseBaseDavinci {
	public String selectUser;
    public int selectIndex;
	public ResponseSelectUserCard(ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList, int turnUserIndex, int selectIndex, String selectUser, int roomNo ) {
		super(DavinciCommon.IDENTIFIER_SELECT_USER_CARD, userList, fieldCardList, turnUserIndex, roomNo);
		this.selectIndex = selectIndex;
		this.selectUser = selectUser;
	}

}
