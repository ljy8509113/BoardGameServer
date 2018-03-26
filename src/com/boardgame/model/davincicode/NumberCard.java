package com.boardgame.model.davincicode;

public class NumberCard {
	private int number;
	private boolean isOpen = false;
	
	public NumberCard(int number) {
		this.number = number;		
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean IsOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
