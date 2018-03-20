package com.boardgame.model;

import com.boardgame.common.UserState;
import io.netty.channel.ChannelHandlerContext;

public class UserInfo {
	public class User{
		UserState status;
		
		String email;
		String nickName;
		boolean isMaster;
	}
	
	private ChannelHandlerContext ctx;
	private User user = new User();
	
	public UserInfo(ChannelHandlerContext ctx, String email, String nickName, boolean isMaster) {
		this.ctx = ctx;
		user.status = UserState.CONNECTION;
		user.email = email;
		user.nickName = nickName;
		user.isMaster = isMaster;		
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public UserState getStatus() {
		return user.status;
	}

	public void setStatus(UserState status) {
		user.status = status;
	}

	public String getEmail() {
		return user.email;
	}

	public void setEmail(String email) {
		user.email = email;
	}

	public String getNickName() {
		return user.nickName;
	}

	public void setNickName(String nickName) {
		user.nickName = nickName;
	}

	public boolean isMaster() {
		return user.isMaster;
	}

	public void setMaster(boolean isMaster) {
		user.isMaster = isMaster;
	}	
}
