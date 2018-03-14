package com.boardgame.model;

import java.io.Serializable;

public class GameRoom implements Serializable{
	private static final long serialVersionUID = 1L;

	Integer no;
	String title;
	Integer gameNo;
	Integer maxUser;
	Integer currentUser;
	String state;
	String masterEmail;
	
	
	public GameRoom() {
	}
	
	public GameRoom(Integer no, String title, Integer gameNo, Integer maxUser, String state, String masterEmail) {
		this.no = no;
		this.title = title;
		this.gameNo = gameNo;
		this.maxUser = maxUser;
		this.state = state;
		this.masterEmail = masterEmail;
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
	public Integer getGameNo() {
		return gameNo;
	}
	public void setGameNo(Integer gameNo) {
		this.gameNo = gameNo;
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
	public String getMasterEmail() {
		return masterEmail;
	}
	public void setMasterEmail(String masterEmail) {
		this.masterEmail = masterEmail;
	}

	public Integer getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Integer currentUser) {
		this.currentUser = currentUser;
	}	
	
	
}
