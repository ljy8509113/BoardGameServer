package com.boardgame.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.UserState;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserInfo;
import com.boardgame.response.ResponseGamingUser;
import com.boardgame.response.ResponseOutRoom;
import com.boardgame.response.ResponseReady;
import com.database.common.ResCode;
import com.database.util.CustomException;

public abstract class BaseController {
	private List<GameRoom> listRoom;
	
	public BaseController() {
		listRoom = new ArrayList<>();
	}
	
	public List<UserInfo> addRoom(GameRoom room) {
		room.setNo(makeRoomNo());
		listRoom.add(room);	
		
		return room.getUserList();		
	}

	public void removeRoom(int roomNo) {
		for(GameRoom room : listRoom) {
			if(room.getNo() == roomNo) {
				listRoom.remove(room);
				break;
			}
		}
	}

	public List<UserInfo.User> addUser(int roomNo, UserInfo info) throws CustomException {
		boolean isAdd = false;
		GameRoom room = getRoom(roomNo);
		
		isAdd = room.addUser(info);
		
		if(isAdd) {
			return room.getResUserList();
		}else {
			throw new CustomException(ResCode.ERROR_CONNECTION_ROOM.getResCode(), ResCode.ERROR_CONNECTION_ROOM.getMessage());
		}		
	}

	public int makeRoomNo() {		
		if(listRoom.size() == 0)
			return 1;
		else
			return listRoom.get(listRoom.size()-1).getNo() + 1;		
	}
	
	
	public List<GameRoom.RoomInfo> getRoomList(int current, int count){
		int endCount = current * count;
		
		if( endCount > listRoom.size()) {
			endCount = listRoom.size();
		}
		
		List<GameRoom.RoomInfo> resultList = new ArrayList<GameRoom.RoomInfo>();
		
		int startIndex = count * (current-1); 
		
		for(int i=startIndex; i<endCount; i++)
		{
			GameRoom room = listRoom.get(i);
					
			
			resultList.add(room.getRoomInfo());
		}
		
		return resultList;
	}
	
	public int getRoomMaxLength() {
		return listRoom.size();
	}
	
	public void setState(int roomNo, int state) throws CustomException {
		GameRoom room = getRoom(roomNo);
		room.setState(state);
	}
	
	public ResponseGamingUser checkGaming(String email) {
		boolean isGaming = false;
		
		loop1:for(GameRoom g : listRoom) {
			List<UserInfo> users = g.getUserList();
			for(UserInfo i : users) {
				if(i.getEmail().equals(email)) {
					isGaming = true;
					break loop1;
				}
			}
		}
		
		ResponseGamingUser res = new ResponseGamingUser(isGaming);
		return res;
	}
	
	public ResponseReady onReadyUser(String email, boolean isReady, int roomNo) throws CustomException {
		GameRoom room = getRoom(roomNo);
		if(isReady)
			room.changeUserState(email, UserState.READ.getValue());
		else
			room.changeUserState(email, UserState.CONNECTION.getValue());
		
		ResponseReady res = new ResponseReady(email, isReady);
		return res;
	}
	
	public ResponseOutRoom onOutRoomUser(String email, int roomNo) throws CustomException {
		GameRoom room = getRoom(roomNo);
		room.removeUser(email);
		ResponseOutRoom res = new ResponseOutRoom(email);
		return res;
	}
	
	//전체 보내기
	public void sendMessage(String msg) {
		for(GameRoom room : listRoom) {
			List<UserInfo> userlist = room.getUserList();
			for(UserInfo info : userlist) {
				info.sendMessage(msg);
			}			
		}
	}
	
	//룸 유저에게만
	public void sendMessage(int roomNo, String msg) throws CustomException {
		GameRoom room = getRoom(roomNo);
		for(UserInfo info : room.getUserList()) {
			info.sendMessage(msg);
		}
	}	
	
	public GameRoom getRoom(int roomNo) throws CustomException {
		for(GameRoom i : listRoom) {
			if(i.getNo() == roomNo) {
				return i;				
			}				
		}		
		throw new CustomException(ResCode.ERROR_NOT_FOUND_ROOM.getResCode(), ResCode.ERROR_NOT_FOUND_ROOM.getMessage());
	}
	
}
