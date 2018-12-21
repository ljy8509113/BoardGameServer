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
			ResponseOtherAccept res = new ResponseOtherAccept();
			RequestController.Instance().response(res, userData.ctx);
		}
		mapUsers.put(info.email, info);
	}
	
	public void disConnection(ChannelHandlerContext ctx) {
		Iterator<String> keys = mapUsers.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			UserData data = mapUsers.get(key);
			
			if(data.ctx == ctx) {
				if(data.state == UserState.NONE) {
					mapUsers.remove(key);
					return;
				}else {
					DavinciCodeController.Instance().disConnectionUser(ctx);
					return;
				}
			}
		}
	}
	
	public UserData getUser(String email) {
		return mapUsers.get(email);
	}
}
