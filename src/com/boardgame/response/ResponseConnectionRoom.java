package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.common.RoomInMax;
import com.boardgame.model.UserDataBase;
import com.database.common.ResCode;

public class ResponseConnectionRoom extends ResponseBase {
	private List<UserDataBase> userList;
	private String title;
	private int roomNo;
	int max;
	int gameNo;
	
	public ResponseConnectionRoom(String title, List<UserDataBase> userList, int roomNo, int gameNo) {
		super(Common.IDENTIFIER_CONNECT_ROOM, ResCode.SUCCESS.getResCode());		
		this.title = title;
		this.userList = userList;
		this.roomNo = roomNo;
		this.gameNo = gameNo;
		max = RoomInMax.MAX.getValue(gameNo);
	}
	
	public ResponseConnectionRoom(int resCode, String message) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode, message);
	}
	
	public List<UserDataBase> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDataBase> userList) {
		this.userList = userList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	
}
