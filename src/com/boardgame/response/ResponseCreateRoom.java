package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseCreateRoom extends ResponseBase{
	String title;
	int total;
    int win;
    int lose;
    int outCount;
	
	public ResponseCreateRoom(int resCode, String title, int total, int win, int lose, int outCount) {
		super(Common.IDENTIFIER_CREATE_ROOM, resCode);
		this.title = title;
		this.total = total;
		this.win = win;
		this.lose = lose;
		this.outCount = outCount;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public int getOutCount() {
		return outCount;
	}

	public void setOutCount(int outCount) {
		this.outCount = outCount;
	}
		
}
