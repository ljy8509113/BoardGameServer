package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseCreateRoom extends ResponseBase{
	String title;
	String nickName;
	
	public ResponseCreateRoom(int resCode, String title, String nickName) {
		super(Common.IDENTIFIER_CREATE_ROOM, resCode);
		this.title = title;
		this.nickName = nickName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
