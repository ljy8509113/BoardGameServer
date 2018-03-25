package com.boardgame.model.davincicode;

public class NumberCard {
	private int number;
	private boolean isJoer;
	private boolean isOpen = false;
	
	public NumberCard() {
	}
	
	public NumberCard(int number, boolean isJoer) {
		this.number = number;
		this.isJoer = isJoer;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean IsJoer() {
		return isJoer;
	}
	public void setJoer(boolean isJoer) {
		this.isJoer = isJoer;
	}

	public boolean IsOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
}
