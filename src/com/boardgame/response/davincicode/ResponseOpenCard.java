package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseOpenCard extends ResponseBase{
	boolean isSuccess;
	int number;
	int index;
	String targetUser;
	String email;
	boolean isLose;
	
	public ResponseOpenCard(String targetUser, int number, int index, boolean isSuccess, String email, boolean isLose) {
		super(Common.IDENTIFIER_OPEN_CARD, ResCode.SUCCESS.getResCode());
		this.targetUser = targetUser;
		this.number = number;
		this.index = index;
		this.isSuccess = isSuccess;
		this.email = email;
		this.isLose = isLose;
	}
}
