package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.UserDataBase;
import com.database.common.ResCode;

public class ResponseRoomPassword extends ResponseBase {
	public int roomNo;
	public String title;
	private List<UserDataBase> userList;
	
	public ResponseRoomPassword(int roomNo, String title, List<UserDataBase> list) {
		super(Common.IDENTIFIER_ROOM_PASSWORD, ResCode.SUCCESS.getResCode());
		this.roomNo = roomNo;
		this.title = title;
		this.userList = list;
	}
	
	public ResponseRoomPassword(int resCode, String message) {
		super(Common.IDENTIFIER_ROOM_PASSWORD, resCode, message);
	}
}
