package com.boardgame.common;

public enum UserState {
	CONNECTION(0),
	GAME_WAITING(1),
	GAME_READY(2),
	PLAING(3),
	DISCONNECTION(4);
	
	int value;
	UserState(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
