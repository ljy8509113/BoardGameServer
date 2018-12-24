package com.boardgame.model;

import com.boardgame.common.UserState;

public class UserDataBase {
	public int state;
	public String email;
	public String nickName;
	public boolean isMaster = false;
	
	public UserDataBase(UserState state, String email, String nickName, boolean isMaster) {
		this.state = state.getValue();
		this.email = email;
		this.nickName = nickName;
		this.isMaster = isMaster;
	}

	public UserState getState() {
		return UserState.getState(state);
	}

	public void setState(UserState state) {
		this.state = state.getValue();
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
	
	
}
