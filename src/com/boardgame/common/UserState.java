package com.boardgame.common;

public enum UserState {
	CONNECTION("C"),
	DISCONNECTION("D");
	
	String value;
	
	UserState(String v){
		this.value = v;
	}
	
	public String getValue() {
		return value;
	}
}
