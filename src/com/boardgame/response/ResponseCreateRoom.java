package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseCreateRoom extends ResponseBase{
	String title;
	
	public ResponseCreateRoom(int resCode, String title) {
		super(Common.IDENTIFIER_CREATE_ROOM, resCode);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
