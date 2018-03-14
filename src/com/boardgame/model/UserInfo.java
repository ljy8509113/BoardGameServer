package com.boardgame.model;

import com.boardgame.common.UserState;

import io.netty.channel.ChannelHandlerContext;

public class UserInfo {
	private Integer gameRoomId = null;
	private String email = null;
	private ChannelHandlerContext ctx;
	private UserState status;
	
	public UserInfo(ChannelHandlerContext ctx, String email) {
		this.ctx = ctx;
		this.email = email;
		status = UserState.CONNECTION;
	}
	
	public Integer getGameRoomId() {
		return gameRoomId;
	}



	public void setGameRoomId(Integer gameRoomId) {
		this.gameRoomId = gameRoomId;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
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

	public void quitRoom() {
		gameRoomId = null;
	}
	
}
