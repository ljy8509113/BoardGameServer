package com.boardgame.model;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.UserState;
import com.boardgame.controller.RequestController;
import com.boardgame.controller.UserController;
import com.boardgame.response.ResponseBase;

import io.netty.channel.ChannelHandlerContext;

public class GameRoom extends Room{
	List<UserInfo> userList;

	public GameRoom() {		
	}

	public GameRoom(Integer no, String title, Integer maxUser, String email, String password, String nickName, ChannelHandlerContext ctx){
		super(no, title, nickName, maxUser, 1, true, password);

		UserInfo info = UserController.Instance().getUserInfo(email);//new UserInfo(ctx, email, nickName, true, UserState.GAME_WAITING);// UserController.Instance().getUser(email);
		info.setState(UserState.GAME_WAITING);
		
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
			list.add(i.getUser());
		}
		return list;
	}

	public Room getRoom() {
		return this;
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
	}

}
