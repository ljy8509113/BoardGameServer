package com.boardgame.model.davincicode;

public class NumberCard {
	private int number;
	private boolean isJoker = false;
	private boolean isOpen = false;
	
	public NumberCard(int number, boolean isJoker) {
		this.number = number;
		this.isJoker = isJoker;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean IsJoker() {
		return isJoker;
	}
	public void setJoker(boolean isJoer) {
		this.isJoker = isJoer;
	}

	public boolean IsOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
