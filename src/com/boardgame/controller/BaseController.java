package com.boardgame.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.UserState;
import com.boardgame.model.GameRoom;
import com.boardgame.model.Room;
import com.boardgame.model.UserData;
import com.boardgame.model.UserInfo;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseGamingUser;
import com.boardgame.response.ResponseOutRoom;
import com.boardgame.response.ResponseReady;
import com.boardgame.response.ResponseRoomInfo;
import com.database.common.ResCode;
import com.database.util.CustomException;

import io.netty.channel.ChannelHandlerContext;

public abstract class BaseController {
	public abstract void reqData(String reqStr, String identifier, ChannelHandlerContext ctx) throws CustomException;

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
	
	public void removeRoom(GameRoom room) {
		listRoom.remove(room);
	}

	public List<UserData> addUser(int roomNo, UserInfo info) throws CustomException {
		boolean isAdd = false;
		GameRoom room = getRoom(roomNo);

		isAdd = room.addUser(info);

		if(isAdd) {
			if(!UserController.Instance().updateState(UserState.GAME_WAITING, info.getEmail())) {
				throw new CustomException(ResCode.ERROR_EMAIL_NOT_FOUND.getResCode(), ResCode.ERROR_EMAIL_NOT_FOUND.getMessage());
			}
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


	public List<Room> getRoomList(int current, int count){
		int endCount = current * count;

		if( endCount > listRoom.size()) {
			endCount = listRoom.size();
		}

		List<Room> resultList = new ArrayList<Room>();

		int startIndex = count * (current-1); 

		for(int i=startIndex; i<endCount; i++)
		{
			GameRoom room = listRoom.get(i);
			resultList.add(room.getRoom());
		}

		return resultList;
	}

	public int getRoomMaxLength() {
		return listRoom.size();
	}

	public ResponseGamingUser checkGaming(String email) {
		UserInfo info = UserController.Instance().getUserInfo(email);
		
		int roomNo = -1;
		boolean isGaming = false;
		
		if(info != null) {
			if(info.getState() == UserState.PLAING.getValue()) {
				Integer no = findRoomNo(email);
				
				if(no == null) {
					UserController.Instance().updateState(UserState.NONE, email);
				}else {
					isGaming = true;
					roomNo = no;
				}
			}
		}
		
		ResponseGamingUser res = new ResponseGamingUser(isGaming, roomNo);
		return res;
	}

	public ResponseRoomInfo onReadyUser(String email, boolean isReady, int roomNo) throws CustomException {
		GameRoom room = getRoom(roomNo);

		if(isReady) {
			room.updateUserState(email, UserState.GAME_READY);
		}else {
			room.updateUserState(email, UserState.GAME_WAITING);
		}
		
//		ResponseReady res = new ResponseReady(email, isReady);
		ResponseRoomInfo res = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
		return res;
	}

	public void onOutRoomUser(String email, int roomNo) throws CustomException {
		GameRoom room = getRoom(roomNo);
		
		ResponseOutRoom res = new ResponseOutRoom();
		UserInfo info = room.getUser(email);
		RequestController.Instance().response(res, info.getCtx());
		
		if( room.getUser(email).isMaster() && room.getUserList().size() > 1) {
			room.removeUser(email);
			room.changeMaster(0);			
		}else {
			room.removeUser(email);
		}
		
		if(room.getUserList().size() == 0)
			removeRoom(room);
		else {
			ResponseRoomInfo resRoomUsers = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
			for(UserInfo i : room.getUserList()) {
				RequestController.Instance().response(resRoomUsers, i.getCtx());
			}
		}		
	}
	
	//전체 보내기
	public void sendMessage(ResponseBase res) {
		for(GameRoom room : listRoom) {
			List<UserInfo> userlist = room.getUserList();
			for(UserInfo info : userlist) {
//				info.sendMessage(res);
				RequestController.Instance().response(res, info.getCtx());
			}			
		}
	}

	//룸 유저에게만
	public void sendMessage(int roomNo, ResponseBase res) throws CustomException {
		GameRoom room = getRoom(roomNo);
		for(UserInfo info : room.getUserList()) {
			//info.sendMessage(res);
			RequestController.Instance().response(res, info.getCtx());
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
	
	public Integer findRoomNo(String email) {
		for(GameRoom g : listRoom) {
			List<UserInfo> users = g.getUserList();
			for(UserInfo i : users) {
				if(i.getEmail().equals(email)) {
					return g.getNo();		
				}
			}
		}		
		return null;
	}
	
	public boolean checkRoomPassword(int roomNo, String password) throws CustomException {
		GameRoom room = getRoom(roomNo);
		
		return room.getPassword().equals(password);
	}
}
