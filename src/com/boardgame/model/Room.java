package com.boardgame.model;

public class Room {
	int gameNo;
	Integer no = null;
	String title;
	String masterUserNickName = "";
	int maxUser = 0;
	public boolean isPlaing = false;
	String password = "";
	
	public Room(Integer no, String title, String masterUserNickName, boolean isPlaing,
			String password, int gameNo) {
		this.no = no;
		this.title = title;
		this.masterUserNickName = masterUserNickName;
		this.isPlaing = isPlaing;
		this.password = password;
		this.gameNo = gameNo;
		
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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
//	public boolean isFullRoom() {
//		if(maxUser > 0)
//			return currentUser == maxUser ? true : false;
//		else
//			return false;
//	}
	
}
