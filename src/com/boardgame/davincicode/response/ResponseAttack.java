package com.boardgame.davincicode.response;

import java.util.ArrayList;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.NumberCard;
import com.boardgame.davincicode.model.UserGameData;

public class ResponseAttack extends ResponseBaseDavinci {
	public String selectUser;
    public int selectIndex;
    public int attackValue;
    public int openIndex; 
	
	public ResponseAttack(ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList, int turnUserIndex, String selectUser, int selectIndex, int attackValue, int roomNo, int openIndex) {
		super(DavinciCommon.IDENTIFIER_ATTACK, userList, fieldCardList, turnUserIndex, roomNo);
		this.selectUser = selectUser;
		this.selectIndex = selectIndex;
		this.attackValue = attackValue;
		this.openIndex = openIndex;
	}
	
	public ResponseAttack(int resCode, String message, ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList) {
		super(DavinciCommon.IDENTIFIER_ATTACK, resCode, message, userList, fieldCardList);		
	}

}
