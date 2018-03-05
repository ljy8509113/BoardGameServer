package com.boardgame.common;

public enum GameState {
	WAITING("W"),
	FULL("F"),
	PLAING("P");
	
	String value;
	GameState(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
