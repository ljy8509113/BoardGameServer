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
}
