package com.boardgame.davincicode.model;

public class NumberCard{
	int index = -1;
	boolean isOpen = false;
	
	public NumberCard(int index) {
		this.index = index;
	}
	
	public NumberCard(int index, boolean isOpen) {
		this.index = index;
		this.isOpen = isOpen;
	}
}
