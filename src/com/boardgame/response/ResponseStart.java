package com.boardgame.response;

import java.util.ArrayList;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.UserGameData;
import com.database.common.ResCode;

public class ResponseStart extends ResponseBase {
	ArrayList<UserGameData> userList;
	int maxCount;
	public ResponseStart(ArrayList<UserGameData> list, int maxCount) {
		super(Common.IDENTIFIER_START, ResCode.SUCCESS.getResCode());
		userList = list;
		this.maxCount = maxCount;
	}
	
	public ResponseStart(int resCode, String message) {
		super(Common.IDENTIFIER_START, resCode, message);
		userList = null;
	}
	
}
