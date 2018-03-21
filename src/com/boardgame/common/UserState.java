package com.boardgame.common;

public enum UserState {
	CONNECTION(0),
	READ(1),
	DISCONNECTION(2);
	
	int value;
	
	UserState(int v){
		this.value = v;
	}
	
	public int getValue() {
		return value;
	}
}
