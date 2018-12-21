package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserDataBase;
import com.database.common.ResCode;

public class ResponseCreateRoom extends ResponseBase{
	private String title;
	private List<UserDataBase> userList;
	private int roomNo;
	
    public ResponseCreateRoom(int resCode, String message) {
    	super(Common.IDENTIFIER_CREATE_ROOM, resCode, message);
    }
    
	public ResponseCreateRoom(String title, List<UserDataBase> userList, int roomNo) {
		super(Common.IDENTIFIER_CREATE_ROOM, ResCode.SUCCESS.getResCode());
		this.userList = userList;
		this.title = title;
		this.roomNo = roomNo;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserDataBase> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDataBase> userList) {
		this.userList = userList;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}	
	
}
