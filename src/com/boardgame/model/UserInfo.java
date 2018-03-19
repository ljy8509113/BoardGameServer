package com.boardgame.model;

import com.boardgame.common.UserState;
import io.netty.channel.ChannelHandlerContext;

public class UserInfo {
	private ChannelHandlerContext ctx;
	private UserState status;
	private RoomUser user;
	
	public UserInfo(ChannelHandlerContext ctx, RoomUser user) {
		this.ctx = ctx;
		status = UserState.CONNECTION;
		this.user = user;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public UserState getStatus() {
		return status;
	}

	public void setStatus(UserState status) {
		this.status = status;
	}

	public RoomUser getUser() {
		return user;
	}

	public void setUser(RoomUser user) {
		this.user = user;
	}
	
}
