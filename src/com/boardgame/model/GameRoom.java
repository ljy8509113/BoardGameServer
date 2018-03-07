package com.boardgame.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.GameState;

public class GameRoom implements Serializable{
	private static final long serialVersionUID = 1L;

	Integer no;
	String title;
	Integer gameNo;
	Integer fullUser;
	String state;
	String masterUuid;
	
	public GameRoom() {
	}
	
	public GameRoom(Integer no, String title, Integer gameNo, Integer fullUser, String state, String masterUuid) {
		this.no = no;
		this.title = title;
		this.gameNo = gameNo;
		this.fullUser = fullUser;
		this.state = state;
		this.masterUuid = masterUuid;
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
	public Integer getFullUser() {
		return fullUser;
	}
	public void setFullUser(Integer fullUser) {
		this.fullUser = fullUser;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMasterUuid() {
		return masterUuid;
	}
	public void setMasterUuid(String masterUuid) {
		this.masterUuid = masterUuid;
	}	
}
