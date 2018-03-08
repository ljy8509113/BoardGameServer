package com.boardgame.model;

import com.boardgame.common.UserState;

import io.netty.channel.ChannelHandlerContext;

public class UserInfo {
	private Integer gameRoomId = null;
	private String uuid = null;
	private ChannelHandlerContext ctx;
	private UserState status;
	
	public UserInfo(ChannelHandlerContext ctx, String uuid) {
		this.ctx = ctx;
		this.uuid = uuid;
		status = UserState.CONNECTION;
	}
	
	public Integer getGameRoomId() {
		return gameRoomId;
	}



	public void setGameRoomId(Integer gameRoomId) {
		this.gameRoomId = gameRoomId;
	}



	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
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
