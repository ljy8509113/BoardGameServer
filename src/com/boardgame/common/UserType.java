package com.boardgame.common;

public enum UserType {
	MASTER(0),
	USER(1),
	COMPUTER(2);
	
	int value;
	UserType(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static UserType getType(int value) {
		switch(value) {
		case 0 :
			return MASTER;
		case 1:
			return USER;
		case 2: 
			return COMPUTER;
		default :
			return USER;
		}
	}
}
