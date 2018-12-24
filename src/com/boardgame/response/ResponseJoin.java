package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseJoin extends ResponseBase {
	boolean isAuto;
	String email;
	String password;
	String nickName;
	public ResponseJoin(boolean isAuto, String email, String password, String nickName) {
		super(Common.IDENTIFIER_JOIN, ResCode.SUCCESS.getResCode());
		this.isAuto = isAuto;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
	}
	
	public ResponseJoin(int resCode, String msg) {
		super(Common.IDENTIFIER_JOIN, resCode, msg);
	}

}
