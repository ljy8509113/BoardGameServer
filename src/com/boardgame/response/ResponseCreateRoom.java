package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserInfo;

public class ResponseCreateRoom extends ResponseBase{
	private String title;
	private List<UserInfo.User> userList;
	
    public ResponseCreateRoom(int resCode, String message) {
    	super(Common.IDENTIFIER_CREATE_ROOM, resCode, message);
    }
    
	public ResponseCreateRoom(int resCode, String title, List<UserInfo.User> userList) {
		super(Common.IDENTIFIER_CREATE_ROOM, resCode);
		this.userList = userList;
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserInfo.User> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfo.User> userList) {
		this.userList = userList;
	}	
	
}
