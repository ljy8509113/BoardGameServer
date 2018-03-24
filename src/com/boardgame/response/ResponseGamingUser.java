package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseGamingUser extends ResponseBase {
	String textMsg = null;
	boolean isGaming = false;
	
	public ResponseGamingUser(boolean isGaming) {
		super(Common.IDENTIFIER_GAMING_USER, ResCode.SUCCESS.getResCode());
		this.isGaming = isGaming;
		this.textMsg = isGaming == true ? "게임 참여중인 방이 존재합니다.\n계속 하시겠습니까?":"";		
	}
	
	public ResponseGamingUser(int resCode, String message) {
		super(Common.IDENTIFIER_GAMING_USER, resCode, message);
	}
	
	public boolean isGaming() {
		return isGaming;
	}
	public void setGaming(boolean isGaming) {
		this.isGaming = isGaming;
	}

	
}
