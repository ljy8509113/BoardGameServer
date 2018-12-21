package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseJoin extends ResponseBase {
	boolean isAuto;
	public ResponseJoin(boolean isAuto) {
		super(Common.IDENTIFIER_JOIN, ResCode.SUCCESS.getResCode());
		this.isAuto = isAuto;
	}
	
	public ResponseJoin(int resCode, String msg) {
		super(Common.IDENTIFIER_JOIN, resCode, msg);
	}

}
