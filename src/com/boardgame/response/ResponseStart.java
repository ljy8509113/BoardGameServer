package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserData;
import com.database.common.ResCode;

public class ResponseStart extends ResponseBase {
	private List<UserData> userList;
	public ResponseStart() {
		super(Common.IDENTIFIER_START, ResCode.SUCCESS.getResCode());
		userList = null;
	}
	
	public ResponseStart(int resCode, String message) {
		super(Common.IDENTIFIER_START, resCode, message);
		userList = null;
	}
	
	public ResponseStart(List<UserData> list, int resCode, String message) {
		super(Common.IDENTIFIER_START, resCode, message);
		userList = list;
	}
}
