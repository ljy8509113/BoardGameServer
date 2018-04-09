package com.boardgame.response;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserInfo;
import com.database.common.ResCode;

public class ResponseRoomUsers extends ResponseBase {
	private List<UserInfo.User> userList;
	
	public ResponseRoomUsers() {
		
	}
	
	public ResponseRoomUsers(List<UserInfo.User> userList) {
		super(Common.IDENTIFIER_ROOM_USERS, ResCode.SUCCESS.getResCode());
		if(userList == null)
			this.userList = new ArrayList<UserInfo.User>();
		else
			this.userList = userList;
	}
	
	public ResponseRoomUsers(int resCode, String message) {
		super(Common.IDENTIFIER_ROOM_USERS, resCode, message);
	}

	public List<UserInfo.User> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfo.User> userList) {
		this.userList = userList;
	}
	
}
