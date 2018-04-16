package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseOtherAccept extends ResponseBase{
	
	public ResponseOtherAccept() {
		super(Common.IDENTIFIER_OTHER_ACCEPT, ResCode.ERROR_OTHER_ACCEPT.getResCode(), ResCode.ERROR_OTHER_ACCEPT.getMessage());
	}
}
