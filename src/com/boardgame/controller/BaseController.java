package com.boardgame.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.boardgame.model.GameRoom;
import com.boardgame.model.RoomUser;
import com.boardgame.model.RoomUserList;
import com.boardgame.model.UserInfo;
import com.boardgame.response.ResponseGamingUser;
import com.database.common.ResCode;
import com.database.util.CustomException;

import io.netty.channel.ChannelHandlerContext;

public abstract class BaseController {
	private HashMap<Integer,RoomUserList> mapUser;
	private List<GameRoom> listRoom;
	
	public BaseController() {
		mapUser = new HashMap<>();
		listRoom = new ArrayList<>();
	}
	
	
	public List<RoomUser> addRoom(GameRoom room, ChannelHandlerContext ctx) {
		room.setNo(makeRoomNo());
		
		RoomUserList roomInfo = new RoomUserList(room);
		mapUser.put(room.getNo(), roomInfo);
		
		listRoom.add(room);	
		
		return roomInfo.getUserList(); //getRoomUserList(room.getGameNo(), room.getNo());		
	}

	public void removeRoom(int roomNo) {
		mapUser.remove(roomNo);
		
		for(GameRoom room : listRoom) {
			if(room.getNo() == roomNo) {
				listRoom.remove(room);
				break;
			}
		}
	}

	public List<RoomUser> addUser(int roomNo, UserInfo info) throws CustomException {
		RoomUserList roomInfo = mapUser.get(roomNo);
		roomInfo.addUser(info);	
		
		return roomInfo.getUserList();
	}

	public int makeRoomNo() {
		
		if(listRoom.size() == 0)
			return 1;
		else
			return listRoom.get(listRoom.size()-1).getNo() + 1;		
	}
	
	public List<GameRoom> getRoomList(int current, int count){
		int endCount = current * count;
		
		if( endCount > listRoom.size()) {
			endCount = listRoom.size();
		}
		
		List<GameRoom> resultList = new ArrayList<GameRoom>();
		
		int startIndex = count * (current-1); 
		
		for(int i=startIndex; i<endCount; i++)
		{
			resultList.add(listRoom.get(i));
		}
		
		return resultList;
	}
	
	public int getRoomMaxLength() {
		return listRoom.size();
	}
	
	public void setState(int roomNo, String state) {
		RoomUserList roomInfo = mapUser.get(roomNo);
		roomInfo.setState(state);
	}
	
	public ResponseGamingUser checkGaming(String email, int gameNo) {
		boolean isGaming = false;
		for(Integer key : mapUser.keySet()) {
			RoomUserList item = mapUser.get(key);
			if(item.checkEmail(email)) {
				isGaming = true;
				break;
			}			
		}
		
		ResponseGamingUser res = new ResponseGamingUser(ResCode.SUCCESS.getResCode(), isGaming);
		return res;
	}
	
	public void sendMessage(String msg) {
		for(int roomNo : mapUser.keySet()) {
			RoomUserList userList = mapUser.get(roomNo);
			userList.sendMessage(msg);
		}
	}
	
	public void sendMessage(int roomNo, String msg) {
		RoomUserList userList = mapUser.get(roomNo);
		userList.sendMessage(msg);		
	}
	
	public List<RoomUser> getRoomUserList(int roomNo){
		return mapUser.get(roomNo).getUserList();		
	}
	
	
}
