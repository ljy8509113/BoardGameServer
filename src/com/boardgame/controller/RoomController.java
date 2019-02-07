package com.boardgame.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.common.UserState;
import com.boardgame.davincicode.common.DavincicodeError;
import com.boardgame.davincicode.response.ResponseGameCardInfo;
import com.boardgame.model.GameRoom;
import com.boardgame.model.Room;
import com.boardgame.model.UserData;
import com.boardgame.model.UserDataBase;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseGamingUser;
import com.boardgame.response.ResponseOutRoom;
import com.boardgame.response.ResponseRoomInfo;
import com.database.common.ResCode;
import com.database.util.CustomException;

public class RoomController {
	private List<GameRoom> listRoom;
	private static RoomController instance= null;

	public static RoomController Instance() {
		if(instance == null) {
			instance = new RoomController();
			instance.listRoom = new ArrayList<GameRoom>();
		}
		return instance;
	}
	
	
	public List<UserData> addRoom(GameRoom room) {
		room.setNo( listRoom.size()+1 );
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

	public List<UserDataBase> addUser(GameRoom room, UserData info) throws CustomException {
		boolean isAdd = false;
		isAdd = room.addUser(info);

		if(isAdd) {
			info.setState(UserState.GAME_WAITING);
			return room.getResUserList();
		}else {
			throw new CustomException(ResCode.ERROR_CONNECTION_ROOM.getResCode(), ResCode.ERROR_CONNECTION_ROOM.getMessage());
		}		
	}

	public List<UserDataBase> addComputer(GameRoom room) throws CustomException{
		if(room.addComputer()) {
			return room.getResUserList();
		}else {
			throw new CustomException(DavincicodeError.ERROR_ADD_COMPUTER.getCode(), DavincicodeError.ERROR_ADD_COMPUTER.getMessage());
		}
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

	public int getRoomSize() {
		return listRoom.size();
	}

	public ResponseGamingUser checkGaming(String email) {
		UserData info = SocketController.Instance().getUser(email);//UserController.Instance().getUserInfo(email);
		
		int roomNo = -1;
		boolean isGaming = false;
		
		if(info != null) {
			if(info.getState() == UserState.PLAING) {
				GameRoom room = findRoom(email);
				
				if(room == null) {
					//UserController.Instance().updateState(UserState.NONE, email);
					info.setState(UserState.NONE);
				}else {
					isGaming = true;
					roomNo = room.getNo();
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
		UserData info = room.getUser(email);
		RequestController.Instance().response(res, info.getCtx());
		
		if( room.getUser(email).isMaster() && room.getUserList().size() > 1) {
			room.removeUser(email);
			room.changeMaster(room.getUserList().get(0).email); //changeMaster(0);			
		}else {
			room.removeUser(email);
		}
		
		if(room.getUserList().size() == 0)
			removeRoom(room);
		else {
			ResponseRoomInfo resRoomUsers = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
			for(UserData i : room.getUserList()) {
				RequestController.Instance().response(resRoomUsers, i.getCtx());
			}
		}		
	}
	
	//전체 보내기
	public void sendMessage(ResponseBase res) {
		for(GameRoom room : listRoom) {
			List<UserData> userlist = room.getUserList();
			for(UserData info : userlist) {
//				info.sendMessage(res);
				RequestController.Instance().response(res, info.getCtx());
			}			
		}
	}

	//룸 유저에게만
	public void sendMessage(int roomNo, ResponseBase res){
		
		try {
			GameRoom room = getRoom(roomNo);
			for(UserData info : room.getUserList()) {
				RequestController.Instance().response(res, info.getCtx());
			}
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public GameRoom findRoom(String email) {
		for(GameRoom g : listRoom) {
			List<UserData> users = g.getUserList();
			for(UserData i : users) {
				if(i.getEmail().equals(email)) {
					return g;		
				}
			}
		}		
		return null;
	}
	
	public boolean checkRoomPassword(int roomNo, String password) throws CustomException {
		GameRoom room = getRoom(roomNo);
		return room.getPassword().equals(password);
	}

	public void disConnection(UserData data) {
		if(data.getState() == UserState.PLAING) {
			data.setState(UserState.DISCONNECT); 
		}else {
			GameRoom room = findRoom(data.email);
			room.removeUser(data.email);
			
			ResponseRoomInfo res = new ResponseRoomInfo(room.getResUserList(), room.getTitle());  
			sendMessage(room.getNo(), res);
		}
	}
}
