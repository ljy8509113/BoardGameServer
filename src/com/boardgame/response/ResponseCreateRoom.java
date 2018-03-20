package com.boardgame.response;

import com.boardgame.common.Common;

public class ResponseCreateRoom extends ResponseBase{
	String title;
//	int total;
//    int win;
//    int lose;
//    int disconnect;
//    int point;
	
    public ResponseCreateRoom(int resCode, String message) {
    	super(Common.IDENTIFIER_CREATE_ROOM, resCode, message);
    }
    
	public ResponseCreateRoom(int resCode, String title, int total, int win, int lose, int disconnect, int point) {
		super(Common.IDENTIFIER_CREATE_ROOM, resCode);
		this.title = title;
//		this.total = total;
//		this.win = win;
//		this.lose = lose;
//		this.disconnect = disconnect;
//		this.point = point;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

//	public int getTotal() {
//		return total;
//	}
//
//	public void setTotal(int total) {
//		this.total = total;
//	}
//
//	public int getWin() {
//		return win;
//	}
//
//	public void setWin(int win) {
//		this.win = win;
//	}
//
//	public int getLose() {
//		return lose;
//	}
//
//	public void setLose(int lose) {
//		this.lose = lose;
//	}
//
//	public int getDisconnect() {
//		return disconnect;
//	}
//
//	public void setDisconnect(int disconnect) {
//		this.disconnect = disconnect;
//	}
//
//	public int getPoint() {
//		return point;
//	}
//
//	public void setPoint(int point) {
//		this.point = point;
//	}
		
	
}
