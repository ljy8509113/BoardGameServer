package com.boardgame.davincicode.response;

import java.util.ArrayList;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.model.UserGameData;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseOpenCard extends ResponseBase{
	boolean isSuccess;
	ArrayList<UserGameData> userList;
	
	public ResponseOpenCard(boolean isSuccess, ArrayList<UserGameData> userList) {
		super(DavinciCommon.IDENTIFIER_OPEN_CARD, ResCode.SUCCESS.getResCode());
		this.userList = userList;
		this.isSuccess = isSuccess;
		
	}
}
