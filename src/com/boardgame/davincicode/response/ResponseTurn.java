package com.boardgame.davincicode.response;

import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseTurn extends ResponseBase {
	int no;
	public ResponseTurn(int no) {
		super(DavinciCommon.IDENTIFIER_TURN, ResCode.SUCCESS.getResCode());
		this.no = no;
	}
}
