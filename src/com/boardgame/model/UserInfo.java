package com.boardgame.model;

import com.boardgame.common.UserState;
import com.boardgame.controller.RequestController;
import com.boardgame.response.ResponseBase;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class UserInfo {
	public class User{
		int state;
		String email;
		String nickName;
		boolean isMaster;
	}
	
	private ChannelHandlerContext ctx;
	private User user = new User();
	
	public UserInfo(ChannelHandlerContext ctx, String email, String nickName, boolean isMaster) {
		this.ctx = ctx;
		user.state = UserState.CONNECTION.getValue();
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

	public int getState() {
		return user.state;
	}

	public void setState(int state) {
		user.state = state;
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
	
	public User getUser() {
		return user;
	}
	
	public void sendMessage(ResponseBase res) {
		String msg = RequestController.Instance().getJson(res);
		ctx.write(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
		ctx.flush();
	}
}
