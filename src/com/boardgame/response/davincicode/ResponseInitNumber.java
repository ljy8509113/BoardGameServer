package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseInitNumber extends ResponseBase {
	String userEmail;
	int number;
	
	public ResponseInitNumber(String userEmail, int number) {
		super(Common.IDENTIFIER_INIT_NUMBER, ResCode.SUCCESS.getResCode());
		this.userEmail = userEmail;
		this.number = number;
	}
	
	public ResponseInitNumber(int resCode, String message) {
		super(Common.IDENTIFIER_INIT_NUMBER, resCode, message);
	}
}
