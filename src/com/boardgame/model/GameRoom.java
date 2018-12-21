package com.boardgame.model;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.UserState;
import com.boardgame.controller.RequestController;
import com.boardgame.controller.SocketController;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;
import com.database.util.CustomException;

import io.netty.channel.ChannelHandlerContext;

public class GameRoom extends Room{
	List<UserData> userList;

	public GameRoom(Integer no, String title, Integer maxUser, String email, String password, String nickName, ChannelHandlerContext ctx){
		super(no, title, nickName, maxUser, false, password);

		UserData info = SocketController.Instance().getUser(email);//UserController.Instance().getUserInfo(email);//new UserInfo(ctx, email, nickName, true, UserState.GAME_WAITING);// UserController.Instance().getUser(email);
		info.state = UserState.GAME_READY;
		info.isMaster = true;
		
		userList = new ArrayList<>();
		userList.add(info);		
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

	public List<UserData> getUserList(){
		return userList;
	}

	public List<UserDataBase> getResUserList(){
		List<UserDataBase> list = new ArrayList<>();
		for(UserData i : userList) {
			UserDataBase data = new UserDataBase(i.state, i.email, i.nickName, i.isMaster); 
			list.add(data);
		}
		return list;
	}

	public Room getRoom() {
//		Room room = new Room(no, title, masterUserNickName, maxUser, currentUser, isPlaing, password);
		return (Room)this;
	}

	public void updateUserState(String email, UserState state) {
		UserData user = getUser(email);
		if(user != null)
			user.state = state;		
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

	public void changeMaster(String email) {
//		UserData user = userList.get(index);
//		user.setMaster(true);
//		user.setState(UserState.GAME_READY);
		
		for(UserData d : this.userList) {
			if(d.email.equals(email)) {
				d.isMaster = true;
				if(d.state == UserState.GAME_WAITING)
					d.state = UserState.GAME_READY;
			}else {
				d.isMaster = false;
			}
		}
	}
	
	public void checkStart() throws CustomException{
		if(userList.size() == 1) {
			throw new CustomException(ResCode.ERROR_NOT_ENOUGH.getResCode(), ResCode.ERROR_NOT_ENOUGH.getMessage());
		}else {
			for(UserData info : userList) {
				if(info.state != UserState.GAME_READY)
					throw new CustomException(ResCode.ERROR_NOT_READY.getResCode(), ResCode.ERROR_NOT_READY.getMessage());
			}
		}
	}

}
