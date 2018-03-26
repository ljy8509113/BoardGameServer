package com.boardgame.model.davincicode;

public class NumberCard{
	private Integer number;
	private boolean isOpen = false;
	private int index = -1;
	
	public NumberCard(int number) {
		this.number = number;		
	}
	
	public Integer getNumber() {
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
	public void setIndex(int index) {
		this.index = index;
	}
	public int getIndex() {
		return index;
	}

}
