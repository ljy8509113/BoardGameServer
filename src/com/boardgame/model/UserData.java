package com.boardgame.model;

public class UserData {
	int state;
	String email;
	String nickName;
	boolean isMaster = false;
	boolean isConnection = true;
	
	public UserData() {		
	}
	
	public UserData(int state, String email, String nickName, boolean isMaster, boolean isConnection) {
		this.state = state;
		this.email = email;
		this.nickName = nickName;
		this.isMaster = isMaster;
		this.isConnection = isConnection;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	public boolean isConnection() {
		return isConnection;
	}

	public void setConnection(boolean isConnection) {
		this.isConnection = isConnection;
	}
		
}
