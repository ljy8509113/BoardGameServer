package com.boardgame.common;

public enum ResCode {
	SUCCESS("0"),
	ERROR_DB("100"),
	ERROR_FULL("101"),
	ERROR_CONNECTION_ROOM("102"),
	ERROR_DECRYPTION("103");
	
	String resCode;
	String message;
	
	ResCode(String resCode){
		this.resCode = resCode;
		
		switch(resCode) {
		case "0":
			message = "성공";
			break;
		case "100":
			message = "데이터를 조회 할 수 없습니다.\n잠시후 다시 시도해주세요.";
			break;
		case "101":
		case "102":
			message = "선택하신 방은 입장 불가능합니다.";
			break;
		case "103":
			message = "로그인중 오류가 발생하였습니다.\n잠시 후 다시 시도해주세요.";
			break;
		}
	}
	
	public String getResCode() {
		return resCode;
	}
	
	public String getMessage() {
		return message;
	}
}
