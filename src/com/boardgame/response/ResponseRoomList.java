package com.boardgame.response;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.model.GameRoom;

public class ResponseRoomList extends ResponseBase {
	private List<GameRoom> list = new ArrayList<GameRoom>();
	private int current;
	private int max;
	
	public ResponseRoomList(String identifier, String resCode, List<GameRoom> list, int current, int max) {
		super(identifier, resCode);
		this.list = list;
		this.current = current;
		this.max = max;
	}
	
	public ResponseRoomList(String identifier, String resCode, String message) {
		super(identifier, resCode, message);
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
