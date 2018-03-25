package com.boardgame.model;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;

public class GameRoom {
	public class RoomInfo{
		Integer no;
		String title;
		String masterUserNickName = "";
		int maxUser = 0;
		int currentUser = 0;
		int state = 0;
		String password = "";
	}
	List<UserInfo> userList;
	RoomInfo room;
	
	public GameRoom() {
	}
	
	public GameRoom(Integer no, String title, Integer maxUser, int state, String email, String password, String nickName, ChannelHandlerContext ctx) {
		room = new RoomInfo();
		
		room.no = no;
		room.title = title;
		room.maxUser = maxUser;
		room.state = state;
		room.password = password;
		room.masterUserNickName = nickName;
		
		UserInfo info = new UserInfo(ctx, email, nickName, true);
		userList = new ArrayList<>();
		userList.add(info);
		
		room.currentUser = userList.size();
	}
	
	public Integer getNo() {
		return room.no;
	}
	public void setNo(Integer no) {
		room.no = no;
	}
	public String getTitle() {
		return room.title;
	}
	public void setTitle(String title) {
		room.title = title;
	}
	public Integer getMaxUser() {
		return room.maxUser;
	}
	public void setMaxUser(Integer maxUser) {
		room.maxUser = maxUser;
	}
	public int getState() {
		return room.state;
	}
	public void setState(int state) {
		room.state = state;
	}
	public Integer getCurrentUser() {
		return room.currentUser;
	}

	public void setCurrentUser(Integer currentUser) {
		room.currentUser = currentUser;
	}

	public String getPassword() {
		return room.password;
	}

	public void setPassword(String password) {
		room.password = password;
	}
	
	public boolean addUser(UserInfo user) {
		for(int i=0; i<userList.size(); i++) {
			UserInfo innerUser = userList.get(i);
			
			if(user.getEmail().equals(innerUser.getEmail())) {
				userList.set(i, user);
				return true;
			}
		}
		
		if(userList.size() >= room.maxUser)
			return false;
		else
			userList.add(user);
		return true;		
	}
	
	public List<UserInfo> getUserList(){
		return userList;
	}
	
	public List<UserInfo.User> getResUserList(){
		List<UserInfo.User> list = new ArrayList<>();
		for(UserInfo i : userList) {
			list.add(i.getUser());
		}
		return list;
	}
	
	public RoomInfo getRoomInfo() {
		return room;
	}
	
	public String getMasterUserNickName() {
		return room.masterUserNickName;
	}
	
	public void changeUserState(String email, int state) {
		UserInfo user = getUser(email);
		if(user != null)
			user.setState(state);
	}
	
	public void removeUser(String email) {
		UserInfo user = getUser(email);
		if(user != null)
			userList.remove(user);
	}
	
	UserInfo getUser(String email) {
		for(UserInfo info : userList) {
			if(info.getEmail().equals(email)) {
				return info;
			}
		}
		return null;
	}
	
	
	
}
