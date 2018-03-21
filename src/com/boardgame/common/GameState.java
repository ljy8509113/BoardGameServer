package com.boardgame.common;

public enum GameState {
	WAITING(0),
	FULL(1),
	PLAING(2);
	
	int value;
	GameState(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
