package com.boardgame.model;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.common.RoomInMax;
import com.boardgame.common.UserType;
import com.boardgame.common.UserState;
import com.boardgame.controller.RequestController;
import com.boardgame.controller.SocketController;
import com.boardgame.controller.game.BaseGame;
import com.boardgame.controller.game.DavinciCodeGame;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;
import com.database.util.CustomException;

import io.netty.channel.ChannelHandlerContext;

public class GameRoom extends Room{
	List<UserData> userList;
	BaseGame game;
	public GameRoom(Integer no, String title, String email, String password, String nickName, ChannelHandlerContext ctx, int gameNo){
		super(no, title, nickName, false, password, gameNo);

		UserData info = SocketController.Instance().getUser(email);//UserController.Instance().getUserInfo(email);//new UserInfo(ctx, email, nickName, true, UserState.GAME_WAITING);// UserController.Instance().getUser(email);
		info.setState(UserState.GAME_READY);
		info.type = UserType.MASTER.getValue();
		
		userList = new ArrayList<>();
		userList.add(info);		
		
		maxUser = RoomInMax.MAX.getValue(gameNo);
		
		switch(gameNo) {
		case Common.GAME_DAVINCICODE :
			game = new DavinciCodeGame(this);
			break;
		}
	}

	public boolean addUser(UserData user) {
		for(int i=0; i<userList.size(); i++) {
			UserData innerUser = userList.get(i);

			if(user.email.equals(innerUser.email)) {
				userList.set(i, user);
				return true;
			}
		}

		if(userList.size() >= maxUser)
			return false;
		else
			userList.add(user);
		return true;		
	}
	
	public boolean addComputer() {
		boolean isAdd = false;
		if(userList.size() == 1) {
			isAdd = true;
		}else {
			isAdd = userList.size() == maxUser ? false : true;
		}
		
		if(isAdd) {
			int count = 1;
			for(UserData user : userList) {
				if( user.getEmail().contains(Common.AI_EMAIL) ) {
					count ++;
				}
			}
			
			String nickName = "Computer" + count; 
			String email = nickName + Common.AI_EMAIL;
			
			UserData com = new UserData(null, email, nickName, UserType.COMPUTER, UserState.GAME_READY );
			userList.add(com);
			return true;
		}else {
			return false;
		}
	}

	public List<UserData> getUserList(){
		return userList;
	}

	public List<UserDataBase> getResUserList(){
		List<UserDataBase> list = new ArrayList<>();
		for(UserData i : userList) {
			UserDataBase data = new UserDataBase(i.getState(), i.email, i.nickName, i.type); 
			list.add(data);
		}
		return list;
	}

	public Room getRoom() {
//		Room room = new Room(no, title, masterUserNickName, maxUser, currentUser, isPlaing, password);
		Room room = new Room(no, title, masterUserNickName, isPlaing, password, gameNo);
		return room;
	}

	public void updateUserState(String email, UserState state) {
		UserData user = getUser(email);
		if(user != null)
			user.setState(state);		
	}

//	public void updateConnectionState(String email, boolean isConnection) {
//		UserData user = getUser(email);
//		if(user != null) {
//			user.setConnection(isConnection);
//			UserController.Instance().updateConnectionState(isConnection, email);
//		}		
//	}

	public void removeUser(String email) {
		UserData user = getUser(email);
		if(user != null)
			userList.remove(user);
	}

	public UserData getUser(String email) {
		for(UserData info : userList) {
			if(info.email.equals(email)) {
				return info;
			}
		}
		return null;
	}
	
	public UserData getUser(ChannelHandlerContext ctx) {
		for(UserData info : userList) {
			if(info.ctx == ctx) {
				return info;
			}
		}
		return null;
	}
	
	public void sendMessage(ResponseBase res) {
		for(UserData info : userList) {
			RequestController.Instance().response(res, info.ctx);
		}
	}
	
	public void sendMessage(ResponseBase res, ChannelHandlerContext ctx) {
		RequestController.Instance().response(res, ctx);
	}

	public void changeMaster(String email) {
		for(UserData d : this.userList) {
			if(d.email.equals(email)) {
				d.type = UserType.MASTER.getValue();
				if(d.getState() == UserState.GAME_WAITING)
					d.setState(UserState.GAME_READY);
			}else {
				d.type = UserType.USER.getValue();
			}
		}
	}
	
	public void checkStart() throws CustomException{
		if(userList.size() == 1) {
			throw new CustomException(ResCode.ERROR_NOT_ENOUGH.getResCode(), ResCode.ERROR_NOT_ENOUGH.getMessage());
		}else {
			for(UserData info : userList) {
				if(info.getState() != UserState.GAME_READY)
					throw new CustomException(ResCode.ERROR_NOT_READY.getResCode(), ResCode.ERROR_NOT_READY.getMessage());
			}
			
//			game.startGame();
		}
	}
	
	public void updateData(String identifier, String json, ChannelHandlerContext ctx) {
		game.setData(identifier, json, ctx);
	}

}
