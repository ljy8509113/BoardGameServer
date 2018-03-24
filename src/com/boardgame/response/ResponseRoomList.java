package com.boardgame.response;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.GameRoom;
import com.database.common.ResCode;

public class ResponseRoomList extends ResponseBase {
	
	private List<GameRoom.RoomInfo> list;
	private int current;
	private int max;
	
	public ResponseRoomList() {		
	}
	
	public ResponseRoomList(List<GameRoom.RoomInfo> list, int current, int max) {
		super(Common.IDENTIFIER_ROOM_LIST, ResCode.SUCCESS.getResCode());
		this.list = list;
		this.current = current;
		this.max = max;
	}
	
	public ResponseRoomList(int resCode, String message) {
		super(Common.IDENTIFIER_ROOM_LIST, resCode, message);
	}

	public List<GameRoom.RoomInfo> getList() {
		return list;
	}

	public void setList(List<GameRoom.RoomInfo> list) {
		this.list = list;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getMax() {
		return this.max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
}
