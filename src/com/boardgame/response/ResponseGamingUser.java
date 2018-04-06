package com.boardgame.response;

import com.boardgame.common.Common;
import com.database.common.ResCode;

public class ResponseGamingUser extends ResponseBase {
	String textMsg = null;
	boolean isGaming = false;
	int roomNo;
	
	public ResponseGamingUser(boolean isGaming, int roomNo) {
		super(Common.IDENTIFIER_GAMING_USER, ResCode.SUCCESS.getResCode());
		this.isGaming = isGaming;
		this.textMsg = isGaming == true ? "게임 참여중인 방이 존재합니다.\n계속 하시겠습니까?":"";
		this.roomNo = roomNo;
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

	public String getTextMsg() {
		return textMsg;
	}

	public void setTextMsg(String textMsg) {
		this.textMsg = textMsg;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}	
}
