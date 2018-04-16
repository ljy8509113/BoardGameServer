package com.boardgame.response;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserData;
import com.database.common.ResCode;

public class ResponseRoomUsers extends ResponseBase {
	private List<UserData> userList;
	
	public ResponseRoomUsers() {		
	}
	
	public ResponseRoomUsers(List<UserData> userList) {
		super(Common.IDENTIFIER_ROOM_USERS, ResCode.SUCCESS.getResCode());
		if(userList == null)
			this.userList = new ArrayList<UserData>();
		else
			this.userList = userList;
	}
	
	public ResponseRoomUsers(int resCode, String message) {
		super(Common.IDENTIFIER_ROOM_USERS, resCode, message);
	}

	public List<UserData> getUserList() {
		return userList;
	}

	public void setUserList(List<UserData> userList) {
		this.userList = userList;
	}
	
}
