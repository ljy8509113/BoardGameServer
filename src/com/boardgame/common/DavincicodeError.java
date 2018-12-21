package com.boardgame.common;

public enum DavincicodeError {
	ALREAD_SELECTED(Common.ALREAD_SELECTED_CODE);
	
	int code;
	String message;

	DavincicodeError(int code){
		this.code = code;

		switch(code) {
		case Common.ALREAD_SELECTED_CODE :
			message = "이미 선택된 카드 입니다.";
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
