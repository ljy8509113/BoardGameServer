package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;

public class ResponseSelectNumber extends ResponseBaseDavinci {
	int index;
	String email;
	boolean isSuccess;
	
	public ResponseSelectNumber(int index, String email, boolean isSuccess, GameCardInfo info) {
		super(Common.IDENTIFIER_SELECT_NUMBER, info);
		this.index = index;
		this.email = email;
		this.isSuccess = isSuccess;
		
	}
	
	public ResponseSelectNumber(int resCode, String message) {
		super(Common.IDENTIFIER_SELECT_NUMBER, resCode, message);
	}
}
