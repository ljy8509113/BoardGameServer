package com.boardgame.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.model.UserInfo;
import io.netty.channel.ChannelHandlerContext;

public class UserManager {
	
	private static UserManager instance = null;
	List<UserInfo> listUser;
	
	public static UserManager Instance() {
		if(instance == null) {
			instance = new UserManager();
			instance.listUser = new ArrayList<UserInfo>();
		}
		return instance;
	}
	
	public void addUser(String uuid, ChannelHandlerContext ctx) {
		UserInfo user = new UserInfo(ctx, uuid);
		listUser.add(user);
	}
}
