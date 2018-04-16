package com.boardgame.model;

public class Room {
	Integer no = null;
	String title;
	String masterUserNickName = "";
	int maxUser = 0;
	int currentUser = 0;
	boolean isPlaing = false;
	String password = "";
	
	public Room() {		
	}
	
	public Room(Integer no, String title, String masterUserNickName, int maxUser, int currentUser, boolean isPlaing,
			String password) {
		this.no = no;
		this.title = title;
		this.masterUserNickName = masterUserNickName;
		this.maxUser = maxUser;
		this.currentUser = currentUser;
		this.isPlaing = isPlaing;
		this.password = password;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMasterUserNickName() {
		return masterUserNickName;
	}

	public void setMasterUserNickName(String masterUserNickName) {
		this.masterUserNickName = masterUserNickName;
	}

	public int getMaxUser() {
		return maxUser;
	}

	public void setMaxUser(int maxUser) {
		this.maxUser = maxUser;
	}

	public int getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(int currentUser) {
		this.currentUser = currentUser;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isFullRoom() {
		if(maxUser > 0)
			return currentUser == maxUser ? true : false;
		else
			return false;
	}
	
}
