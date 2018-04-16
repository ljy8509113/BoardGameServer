package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserData;
import com.database.common.ResCode;

public class ResponseConnectionRoom extends ResponseBase {
	private List<UserData> userList;
	private String title;
	private int roomNo;
	
	public ResponseConnectionRoom(String title, List<UserData> userList, int roomNo) {
		super(Common.IDENTIFIER_CONNECT_ROOM, ResCode.SUCCESS.getResCode());		
		this.title = title;
		this.userList = userList;
		this.roomNo = roomNo;
	}
	
	public ResponseConnectionRoom(int resCode, String message) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode, message);
	}
	
	public List<UserData> getUserList() {
		return userList;
	}

	public void setUserList(List<UserData> userList) {
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
