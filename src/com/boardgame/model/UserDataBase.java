package com.boardgame.model;

import com.boardgame.common.UserType;
import com.boardgame.common.UserState;

public class UserDataBase {
	public int state;
	public String email;
	public String nickName;
	public int type;
	
	public UserDataBase(UserState state, String email, String nickName, int type) {
		this.state = state.getValue();
		this.email = email;
		this.nickName = nickName;
		this.type = type;
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

	public UserType getType() {
		return UserType.getType(type);
	}

	public void setMode(UserType type) {
		this.type = type.getValue();
	}
	
	public boolean isMaster() {
		if(UserType.getType(type) == UserType.MASTER)
			return true;
		else
			return false;
	}
}
