package com.boardgame.model;

public class RoomUser {
	private String email;
	private String nickName;
	private boolean isMaster;
	
	private int totalCount;
	private int win;
	private int lose;
	private int discount;
	
	public RoomUser() {		
	}
	
	public RoomUser(String email, String nickName, boolean isMaster, int totalCount, int win, int lose, int discount) {
		this.email = email;
		this.nickName = nickName;
		this.isMaster = isMaster;
		this.totalCount = totalCount;
		this.win = win;
		this.lose = lose;
		this.discount = discount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
}
