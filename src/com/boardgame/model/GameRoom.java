package com.boardgame.model;

import java.util.ArrayList;
import java.util.List;

public class GameRoom {
	Integer no;
	String title;
	Integer maxUser;
	Integer currentUser;
	String state;
	String password;
	List<UserInfo> userList = new ArrayList<>();
	
	public GameRoom() {
	}
	
	public GameRoom(Integer no, String title, Integer maxUser, String state, String masterEmail, String password) {
		this.no = no;
		this.title = title;
		this.maxUser = maxUser;
		this.state = state;
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
	public Integer getMaxUser() {
		return maxUser;
	}
	public void setMaxUser(Integer maxUser) {
		this.maxUser = maxUser;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Integer currentUser) {
		this.currentUser = currentUser;
	}

	public String getMasterNickName() {
		return masterNickName;
	}

	public void setMasterNickName(String masterNickName) {
		this.masterNickName = masterNickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
