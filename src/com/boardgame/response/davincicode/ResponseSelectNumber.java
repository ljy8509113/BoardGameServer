package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseSelectNumber extends ResponseBase {
	String userEmail;
	int number;
	public ResponseSelectNumber(String userEmail, int number) {
		super(Common.IDENTIFIER_SELECT_NUMBER, ResCode.SUCCESS.getResCode());
		this.userEmail = userEmail;
		this.number = number;
	}
	
	public ResponseSelectNumber(int resCode, String message) {
		super(Common.IDENTIFIER_SELECT_NUMBER, resCode, message);
	}
}
