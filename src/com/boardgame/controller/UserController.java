package com.boardgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.boardgame.common.UserState;
import com.boardgame.model.UserInfo;
import com.boardgame.response.ResponseOtherAccept;

public class UserController {
	private static UserController instance = null;
	
	Map<String, UserInfo> mapUsers;
	
	public static UserController Instance() {
		if(instance == null) {
			instance = new UserController();
			instance.mapUsers = new HashMap<>();
		}
		return instance;
	}
	
	public void addUser(UserInfo info) {
		UserInfo i = mapUsers.get(info.getEmail());
		if(i != null) {
			ResponseOtherAccept res = new ResponseOtherAccept();
			RequestController.Instance().response(res, i.getCtx());
			
			mapUsers.remove(info.getEmail());			
		}
		mapUsers.put(info.getEmail(), info);		
	}
	
	public void removeUser(String email) {
		mapUsers.remove(email);
	}
	
	public boolean updateState(UserState state, String email) {
		UserInfo info = getUserInfo(email);
		if(info != null) {
			info.setState(state);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean updateConnectionState(boolean isConnection, String email) {
		UserInfo info = getUserInfo(email);
		if(info != null) {
			info.setConnection(isConnection);
			return true;
		}else {
			return false;
		}
	}
	
	public UserInfo getUserInfo(String email) {
		return mapUsers.get(email);		
	}
	
}
