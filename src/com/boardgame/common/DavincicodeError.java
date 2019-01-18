package com.boardgame.common;

public enum DavincicodeError {
	ALREAD_SELECTED(1000),
	ERROR_ADD_COMPUTER(1001);
	
	int code;
	String message;

	DavincicodeError(int code){
		this.code = code;

		switch(code) {
		case 1000 :
			message = "이미 선택된 카드 입니다.";
			break;
		case 1001 :
			message = "Computer 추가를 실패하였습니다.";
			break;
		}
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
