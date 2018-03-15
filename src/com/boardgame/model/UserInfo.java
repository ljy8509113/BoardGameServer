package com.boardgame.model;

import com.boardgame.common.UserState;

import io.netty.channel.ChannelHandlerContext;

public class UserInfo {
	private Integer gameRoomId = null;
	private String email = null;
	private String nickName;
	private ChannelHandlerContext ctx;
	private UserState status;
	
	//게임 전적
	private int totalCount;
	private int win;
	private int lose;
	private float winningRate;
	
	public UserInfo(ChannelHandlerContext ctx, String email, String nickName) {
		this.ctx = ctx;
		this.email = email;
		this.nickName = nickName;
		status = UserState.CONNECTION;
	}
	
	public void setScore(int total, int win, int lose) {
		this.totalCount = total;
		this.win = win;
		this.lose = lose;
		if(total > 0 && win > 0)
			this.winningRate = win/total;
		else
			this.winningRate = 0;
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
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public float getWinningRate() {
		return winningRate;
	}

	public void setWinningRate(float winningRate) {
		this.winningRate = winningRate;
	}

	public void quitRoom() {
		gameRoomId = null;
	}
	
}
