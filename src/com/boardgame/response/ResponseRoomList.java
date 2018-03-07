package com.boardgame.response;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.model.GameRoom;

public class ResponseRoomList extends ResponseBase {
	private List<GameRoom> list = new ArrayList<GameRoom>();
	 
	public ResponseRoomList(String identifier, String resCode, List<GameRoom> list) {
		super(identifier, resCode);
		this.list = list;
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
	
	
}
