package com.boardgame.model;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.UserState;
import com.boardgame.controller.RequestController;
import com.boardgame.controller.UserController;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;
import com.database.util.CustomException;

import io.netty.channel.ChannelHandlerContext;

public class GameRoom extends Room{
	List<UserInfo> userList;

	public GameRoom() {		
	}

	public GameRoom(Integer no, String title, Integer maxUser, String email, String password, String nickName, ChannelHandlerContext ctx){
		super(no, title, nickName, maxUser, 1, false, password);

		UserInfo info = UserController.Instance().getUserInfo(email);//new UserInfo(ctx, email, nickName, true, UserState.GAME_WAITING);// UserController.Instance().getUser(email);
		info.setState(UserState.GAME_READY);
		info.setMaster(true);
		
		userList = new ArrayList<>();
		userList.add(info);

		setCurrentUser(userList.size());
	}

	public boolean addUser(UserInfo user) {
		for(int i=0; i<userList.size(); i++) {
			UserInfo innerUser = userList.get(i);

			if(user.getEmail().equals(innerUser.getEmail())) {
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

	public List<UserInfo> getUserList(){
		return userList;
	}

	public List<UserData> getResUserList(){
		List<UserData> list = new ArrayList<>();
		for(UserInfo i : userList) {
			UserData data = new UserData(i.state, i.getEmail(), i.getNickName(), i.isMaster, i.isConnection);
			list.add(data);
		}
		return list;
	}

	public Room getRoom() {
		Room room = new Room(no, title, masterUserNickName, maxUser, currentUser, isPlaing, password);
		return room;
	}

	public void updateUserState(String email, UserState state) {
		UserInfo user = getUser(email);
		if(user != null)
			user.setState(state);		
	}

	public void updateConnectionState(String email, boolean isConnection) {
		UserInfo user = getUser(email);
		if(user != null) {
			user.setConnection(isConnection);
			UserController.Instance().updateConnectionState(isConnection, email);
		}		
	}

	public void removeUser(String email) {
		UserInfo user = getUser(email);
		if(user != null)
			userList.remove(user);
	}

	public UserInfo getUser(String email) {
		for(UserInfo info : userList) {
			if(info.getEmail().equals(email)) {
				return info;
			}
		}
		return null;
	}

	public void sendMessage(ResponseBase res) {
		for(UserInfo info : userList) {
			RequestController.Instance().response(res, info.getCtx());
		}
	}

	public void changeMaster(int index) {
		UserInfo user = userList.get(index);
		user.setMaster(true);
		user.setState(UserState.GAME_READY);
	}
	
	public void checkStart() throws CustomException{
		for(UserInfo info : userList) {
			if(info.isConnection == false) 
				throw new CustomException(ResCode.ERROR_DISCONNECT_USER.getResCode(), ResCode.ERROR_DISCONNECT_USER.getMessage());
			else if(info.state != UserState.GAME_READY.getValue())
				throw new CustomException(ResCode.ERROR_NOT_READY.getResCode(), ResCode.ERROR_NOT_READY.getMessage());
		}
	}

}
