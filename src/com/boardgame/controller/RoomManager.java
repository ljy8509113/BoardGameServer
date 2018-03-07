package com.boardgame.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boardgame.common.Common;
import com.boardgame.common.GameState;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserInfo;

import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class RoomManager {
	class RoomUserList{
		int roomNo;
		Map<String, UserInfo> mapInfo = new HashMap<>();
		GameRoom room;

		public RoomUserList(GameRoom room) {
			this.room = room;
			this.roomNo = room.getNo();
		}

		public boolean addUser(UserInfo info) {
			if(room.getFullUser() > mapInfo.size())
				mapInfo.put(info.getUuid(), info);
			else if( room.getState().equals(GameState.WAITING.getValue()) != false)
				return false;
			else 
				return false;

			return true;
		}

		public void removeUser(UserInfo info) {
			mapInfo.remove(info.getUuid());
		}

		public void setState(String state) {
			room.setState(state);
		}

		public String getState() {
			return room.getState();
		}

		public void sendMessage(String res) {
			for(String uuid : mapInfo.keySet()) {
				UserInfo info = mapInfo.get(uuid);
				info.getCtx().write(Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
				info.getCtx().flush();
			}
		}

	}

	private static RoomManager instance = null;
	private HashMap<Integer,RoomUserList> mapDavincicodeUser = new HashMap<>();
	private List<GameRoom> listDavincicodeRoom = new ArrayList<>();
	
	public static RoomManager Instance() {
		if(instance == null)
			instance = new RoomManager();

		return instance;
	}

	public void addRoom(GameRoom room) {
		switch(room.getGameNo()) {

		case Common.GAME_DAVINCICODE :
			room.setNo(mapDavincicodeUser.size() + 1);
			RoomUserList roomInfo = new RoomUserList(room);
			mapDavincicodeUser.put(room.getNo(), roomInfo);
			break;
		}
	}

	public void removeRoom(int gameNo, int roomNo) {
		Map<Integer, RoomUserList> map = getMap(gameNo);
		map.remove(roomNo);
		
		

	}

	public boolean addUser(int gameNo, int roomNo, UserInfo info) {
		RoomUserList roomInfo = getMap(gameNo).get(roomNo);
		return roomInfo.addUser(info);		
	}

	public List<GameRoom> getRoomList(int gameNo){
		List<GameRoom> list = new ArrayList<>();
		
		Map<Integer, RoomUserList> map = getMap(gameNo);
		
		return null;
	}
	
	Map<Integer, RoomUserList> getMap(int gameNo){
		switch(gameNo) {
		case Common.GAME_DAVINCICODE:
			return mapDavincicodeUser;
		}
		
		return null;
	}
	
	
	
	public void setState(int gameNo, int roomNo, String state) {
		switch(gameNo) {
		case Common.GAME_DAVINCICODE:
			RoomUserList roomInfo = mapDavincicodeUser.get(roomNo);
			roomInfo.setState(state);			
		}
	}

	public void sendMessage(String msg) {
		for(int roomNo : mapDavincicodeUser.keySet()) {
			sendMessage(mapDavincicodeUser, roomNo, msg);
		}
	}

	public void sendMessage(int gameNo, String msg) {
		switch(gameNo) {

		case Common.GAME_DAVINCICODE:
			for(int roomNo : mapDavincicodeUser.keySet()) {
				sendMessage(mapDavincicodeUser, roomNo, msg);
			}
			break;
		}
	}

	public void sendMessage(int gameNo, int roomNo, String msg) {

		switch(gameNo) {

		case Common.GAME_DAVINCICODE:
			sendMessage(mapDavincicodeUser, roomNo, msg);
			break;
		}
	}

	void sendMessage(Map<Integer, RoomUserList> map, Integer roomNo, String msg) {
		RoomUserList info = map.get(roomNo);
		if(info != null)
			info.sendMessage(msg);
	}

}
