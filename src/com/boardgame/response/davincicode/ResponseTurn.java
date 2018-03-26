package com.boardgame.response.davincicode;

import com.boardgame.common.Common;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseTurn extends ResponseBase {
	int no;
	public ResponseTurn(int no) {
		super(Common.IDENTIFIER_TURN, ResCode.SUCCESS.getResCode());
		this.no = no;
	}
}
