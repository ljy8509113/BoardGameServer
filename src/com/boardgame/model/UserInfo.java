package com.boardgame.model;

import com.boardgame.common.UserState;

import io.netty.channel.ChannelHandlerContext;

public class UserInfo extends UserData{
	ChannelHandlerContext ctx;
	
	public UserInfo(ChannelHandlerContext ctx, String email, String nickName, boolean isMaster, UserState state) {
		super(state, email, nickName, isMaster, true);
		this.ctx = ctx;
	}
	
	public UserInfo() {		
	}
	
	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public UserData getUser() {
		return this;
	}
	
}
