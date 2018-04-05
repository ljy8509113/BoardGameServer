package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserInfo;
import com.database.common.ResCode;

public class ResponseConnectionRoom extends ResponseBase {
	private List<UserInfo.User> userList;
	private String title;
	private int roomNo;
	
	public ResponseConnectionRoom(String title, List<UserInfo.User> userList, int roomNo) {
		super(Common.IDENTIFIER_CONNECT_ROOM, ResCode.SUCCESS.getResCode());		
		this.title = title;
		this.userList = userList;
		this.roomNo = roomNo;
	}
	
	public ResponseConnectionRoom(int resCode, String message) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode, message);
	}
	
	public List<UserInfo.User> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfo.User> userList) {
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
