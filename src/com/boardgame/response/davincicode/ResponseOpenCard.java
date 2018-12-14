package com.boardgame.response.davincicode;

import java.util.ArrayList;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.UserGameData;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseOpenCard extends ResponseBase{
	boolean isSuccess;
	ArrayList<UserGameData> userList;
	
	public ResponseOpenCard(boolean isSuccess, ArrayList<UserGameData> userList) {
		super(Common.IDENTIFIER_OPEN_CARD, ResCode.SUCCESS.getResCode());
		this.userList = userList;
		this.isSuccess = isSuccess;
		
	}
}
