package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserData;
import com.database.common.ResCode;

public class ResponseRoomInfo extends ResponseBase {
	private List<UserData> userList;
	private String title;
	
	public ResponseRoomInfo() {		
	}
	
	public ResponseRoomInfo(List<UserData> userList, String title) {
		super(Common.IDENTIFIER_ROOM_INFO, ResCode.SUCCESS.getResCode());
		this.userList = userList;
		this.title = title;
	}
	
	public ResponseRoomInfo(int resCode, String message) {
		super(Common.IDENTIFIER_ROOM_INFO, resCode, message);
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
}
