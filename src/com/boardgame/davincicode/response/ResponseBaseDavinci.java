package com.boardgame.davincicode.response;

import java.util.ArrayList;

import com.boardgame.davincicode.model.NumberCard;
import com.boardgame.davincicode.model.UserGameData;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseBaseDavinci extends ResponseBase {
	public ArrayList<UserGameData> userList;
	public ArrayList<NumberCard> fieldCardList;
	public int turnUserIndex;
	public int roomNo;
	
	public ResponseBaseDavinci(String identifier, ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList, int turnUserIndex, int roomNo) {
		super(identifier, ResCode.SUCCESS.getResCode());
		this.userList = userList;
		this.fieldCardList = fieldCardList;
		this.turnUserIndex = turnUserIndex;
		this.roomNo = roomNo;
	}
	
	public ResponseBaseDavinci(String identifier, int resCode, String message) {
		super(identifier, resCode, message);
	}
	
	public ResponseBaseDavinci(String identifier, int resCode, String message, ArrayList<UserGameData> userList, ArrayList<NumberCard>fieldCardList) {
		super(identifier, resCode, message);
		this.userList = userList;
		this.fieldCardList = fieldCardList;
				
	}
}
