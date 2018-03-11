package com.boardgame.response;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.GameRoom;

public class ResponseRoomList extends ResponseBase {
	private List<GameRoom> list = new ArrayList<GameRoom>();
	private int current;
	private int max;
	
	public ResponseRoomList(String resCode, List<GameRoom> list, int current, int max) {
		super(Common.IDENTIFIER_ROOM_LIST, resCode);
		this.list = list;
		this.current = current;
		this.max = max;
	}
	
	public ResponseRoomList(String resCode, String message) {
		super(Common.IDENTIFIER_ROOM_LIST, resCode, message);
	}

	public List<GameRoom> getList() {
		return list;
	}

	public void setList(List<GameRoom> list) {
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
