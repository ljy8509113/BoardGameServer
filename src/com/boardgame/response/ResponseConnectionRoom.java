package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.RoomUser;

public class ResponseConnectionRoom extends ResponseBase {
	private List<RoomUser> userList;
	
	public ResponseConnectionRoom(int resCode, List<RoomUser> userList) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode);		
		this.userList = userList;
	}
	
	public ResponseConnectionRoom(int resCode, String message) {
		super(Common.IDENTIFIER_CONNECT_ROOM, resCode, message);
	}
	
	public List<RoomUser> getUserList() {
		return userList;
	}

	public void setUserList(List<RoomUser> userList) {
		this.userList = userList;
	}

}
