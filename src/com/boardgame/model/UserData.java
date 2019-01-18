package com.boardgame.model;

import com.boardgame.common.UserType;
import com.boardgame.common.UserState;

import io.netty.channel.ChannelHandlerContext;

public class UserData extends UserDataBase{
	public ChannelHandlerContext ctx;
	
	public UserData(ChannelHandlerContext ctx, String email, String nickName, UserType type, UserState state) {
		super(state, email, nickName, type.getValue());
		this.ctx = ctx;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
	public boolean isSameCtx(ChannelHandlerContext ctx) {
		return this.ctx == ctx;
	}
	
}
