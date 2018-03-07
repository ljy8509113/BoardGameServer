package com.boardgame.response;

public class ResponseGamingUser extends ResponseBase {
	String textMsg = null;
	boolean isGaming = false;
	public ResponseGamingUser(String identifier, String resCode) {
		super(identifier, resCode);
		this.textMsg = isGaming == true ? "게임 참여중인 방이 존재합니다.\n계속 하시겠습니까?":"";		
	}
	
	public boolean isGaming() {
		return isGaming;
	}
	public void setGaming(boolean isGaming) {
		this.isGaming = isGaming;
	}

	
}
