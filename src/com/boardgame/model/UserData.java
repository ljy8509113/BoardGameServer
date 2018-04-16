package com.boardgame.model;

import com.boardgame.common.UserState;

public class UserData {
	UserState state;
	String email;
	String nickName;
	boolean isMaster = false;
	boolean isConnection;
	
	public UserData() {		
	}
	
	public UserData(UserState state, String email, String nickName, boolean isMaster, boolean isConnection) {
		this.state = state;
		this.email = email;
		this.nickName = nickName;
		this.isMaster = isMaster;
		this.isConnection = isConnection;
	}

	public UserState getState() {
		return state;
	}

	public void setState(UserState state) {
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
