package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserInfo;

public class ResponseConnectionRoom extends ResponseBase {
	private List<UserInfo.User> userList;
	private String title;
	
	public ResponseConnectionRoom(int resCode, String title, List<UserInfo.User> userList) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode);		
		this.title = title;
		this.userList = userList;
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
	
	

}
