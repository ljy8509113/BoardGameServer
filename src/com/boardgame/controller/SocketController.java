package com.boardgame.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.boardgame.common.UserState;
import com.boardgame.model.UserData;
import com.boardgame.response.ResponseOtherAccept;

import io.netty.channel.ChannelHandlerContext;

public class SocketController {
	
	private static SocketController instance = null;
	HashMap<String, UserData> mapUsers;
	
	public static SocketController Instance() {
		if(instance == null) {
			instance = new SocketController();
			instance.mapUsers = new HashMap<String, UserData>();
		}
		return instance;
	}
	
	public void connection(UserData info) {
		UserData userData = mapUsers.get(info.email);
		if(userData != null) {
			
			if(userData.state == UserState.DISCONNECT.getValue()) {
				
			}else {
				
			}
			
			ResponseOtherAccept res = new ResponseOtherAccept();
			RequestController.Instance().response(res, userData.ctx);
		}
		mapUsers.put(info.email, info);
	}
	
	public void disConnection(ChannelHandlerContext ctx) {
		System.out.println("dis mapUser : " + mapUsers);
		Iterator<String> keys = mapUsers.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			UserData data = mapUsers.get(key);
			
			if(data.ctx == ctx) {
				if(data.getState() == UserState.NONE) {
					mapUsers.remove(key);
				}else if(data.getState() == UserState.GAME_READY || data.getState() == UserState.GAME_WAITING){
					RoomController.Instance().disConnection(data);
					mapUsers.remove(key);
				}else if(data.getState() == UserState.PLAING) {
					RoomController.Instance().disConnection(data);
				}
			}
		}
	}
	
	public UserData getUser(String email) {
		return mapUsers.get(email);
	}
}
