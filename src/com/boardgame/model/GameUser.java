package com.boardgame.model;

import java.io.Serializable;

public class GameUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	Integer gameRoom;
	String userUuid;
	String masterUuid;
	
	public GameUser() {		
	}

	public GameUser(Integer gameRoom, String userUuid) {
		super();
		this.gameRoom = gameRoom;
		this.userUuid = userUuid;
	}

	public Integer getGameRoom() {
		return gameRoom;
	}

	public void setGameRoom(Integer gameRoom) {
		this.gameRoom = gameRoom;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	
	public String getMasterUuid() {
		return masterUuid;
	}

	public void setMasterUuid(String masterUuid) {
		this.masterUuid = masterUuid;
	}
}
