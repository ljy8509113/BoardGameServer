package com.boardgame.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boardgame.common.Common;
import com.boardgame.model.GameRoom;
import com.boardgame.model.RoomUserList;
import com.boardgame.model.UserInfo;
import com.boardgame.response.ResponseGamingUser;
import com.database.common.ResCode;
import com.database.util.CustomException;

import io.netty.channel.ChannelHandlerContext;

public class RoomManager {
	private static RoomManager instance = null;
	private HashMap<Integer,RoomUserList> mapDavincicodeUser = new HashMap<>();
	private List<GameRoom> listDavincicodeRoom = new ArrayList<>();
	
	public static RoomManager Instance() {
		if(instance == null) {
			instance = new RoomManager();
//			for(int i=1; i<=87; i++) {
//				GameRoom room = new GameRoom(i, "test-"+i, 1, 2, GameState.WAITING.getValue(), "test_master_"+i);
//				instance.addRoom(room);
//			}
		}
		return instance;
	}

	public void addRoom(GameRoom room) {
		Map<Integer, RoomUserList> map = getMap( room.getGameNo() );
		room.setNo(makeRoomNo(room.getGameNo()));
		
		RoomUserList roomInfo = new RoomUserList(room);
		map.put(room.getNo(), roomInfo);
		
		List<GameRoom> list = getRoomList(room.getGameNo());
		list.add(room);		
	}

	public void removeRoom(int gameNo, int roomNo) {
		Map<Integer, RoomUserList> map = getMap(gameNo);
		map.remove(roomNo);
		
		List<GameRoom> list = getRoomList(gameNo);
		for(GameRoom room : list) {
			if(room.getNo() == roomNo) {
				list.remove(room);
				break;
			}
		}
	}

	public void addUser(int gameNo, int roomNo, UserInfo info) throws CustomException {
		RoomUserList roomInfo = getMap(gameNo).get(roomNo);
		roomInfo.addUser(info);		
	}

	public int makeRoomNo(int gameNo) {
		List<GameRoom> list = getRoomList(gameNo);
		if(list.size() == 0)
			return 1;
		else
			return list.get(list.size()-1).getNo() + 1;		
	}
	
	Map<Integer, RoomUserList> getMap(int gameNo){
		switch(gameNo) {
		case Common.GAME_DAVINCICODE:
			return mapDavincicodeUser;
		}
		
		return null;
	}
	
	public List<GameRoom> getRoomList(int gameNo){
		switch(gameNo) {
			case Common.GAME_DAVINCICODE :
				return listDavincicodeRoom;				
		}		
		return null;
	}
	
	public List<GameRoom> getRoomList(int gameNo, int current, int count){
		List<GameRoom> list = getRoomList(gameNo);
		int endCount = current * count;
		
		if( endCount > list.size()) {
			endCount = list.size();
		}
		
		List<GameRoom> resultList = new ArrayList<GameRoom>();
		
		int startIndex = count * (current-1); 
		
		for(int i=startIndex; i<endCount; i++)
		{
			resultList.add(list.get(i));
		}
		
		return resultList;
	}
	
	public int getRoomMaxLength(int gameNo) {
		return getRoomList(gameNo).size();
	}
	
	public void setState(int gameNo, int roomNo, String state) {
		switch(gameNo) {
		case Common.GAME_DAVINCICODE:
			RoomUserList roomInfo = mapDavincicodeUser.get(roomNo);
			roomInfo.setState(state);			
		}
	}
	
	public ResponseGamingUser checkGaming(String uuid, int gameNo) {
		Map<Integer, RoomUserList> map = getMap(gameNo);
		
		boolean isGaming = false;
		for(Integer key : map.keySet()) {
			RoomUserList item = map.get(key);
			if(item.checkUuid(uuid)) {
				isGaming = true;
				break;
			}			
		}
		
		ResponseGamingUser res = new ResponseGamingUser(ResCode.SUCCESS.getResCode(), isGaming);
		return res;
	}
	
	public void moveRoom(String uuid, int gameNo, ChannelHandlerContext ctx) {
		Map<Integer, RoomUserList> map = getMap(gameNo);
		
		for(Integer key : map.keySet()) {
			RoomUserList item = map.get(key);
			if(item.checkUuid(uuid)) {
				
				break;
			}			
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
