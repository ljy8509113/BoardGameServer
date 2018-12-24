package com.boardgame.common;

public enum UserState {
	NONE(0),
	GAME_WAITING(1),
	GAME_READY(2),
	PLAING(3),
	DISCONNECT(4);
	
	
	int value;
	UserState(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static UserState getState(int value) {
		switch(value) {
		case 0 :
			return NONE;
		case 1:
			return GAME_WAITING;
		case 2: 
			return GAME_READY;
		case 3:
			return PLAING;
		case 4:
			return DISCONNECT;
			default :
				return NONE;
		}
	}
}
