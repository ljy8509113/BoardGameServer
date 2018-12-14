package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseSelectNumber extends ResponseBase {
	int index;
	String email;
	boolean isSuccess;
	public ResponseSelectNumber(int index, String email, boolean isSuccess) {
		super(Common.IDENTIFIER_SELECT_NUMBER, ResCode.SUCCESS.getResCode());
		this.index = index;
		this.email = email;
		this.isSuccess = isSuccess;
	}
	
	public ResponseSelectNumber(int resCode, String message) {
		super(Common.IDENTIFIER_SELECT_NUMBER, resCode, message);
	}
}
