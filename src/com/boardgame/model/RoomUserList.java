package com.boardgame.model;

import java.util.HashMap;
import java.util.Map;

import com.database.common.ResCode;
import com.database.util.CustomException;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class RoomUserList {
	String masterEmail;
	Map<String, UserInfo> mapUsers = new HashMap<>();
	GameRoom room;

	public RoomUserList(GameRoom room) {
		this.room = room;
		this.masterEmail = room.getMasterEmail();
	}

	public void addUser(UserInfo info) throws CustomException {
		if(room.getMaxUser() <= mapUsers.size())
			throw new CustomException(ResCode.ERROR_FULL.getResCode(), ResCode.ERROR_FULL.getMessage());
		else 
			mapUsers.put(info.getEmail(), info);
	}

	public void removeUser(UserInfo info) {
		mapUsers.remove(info.getEmail());
	}

	public void setState(String state) {
		room.setState(state);
	}

	public String getState() {
		return room.getState();
	}

	public void sendMessage(String res) {
		for(String uuid : mapUsers.keySet()) {
			UserInfo info = mapUsers.get(uuid);
			ChannelFuture ex = info.getCtx().write(Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
			info.getCtx().flush();
		}
	}
	
	public boolean checkUuid(String email) {
		UserInfo info = mapUsers.get(email);
		if(info == null)
			return false;
		else
			return true;
	}
	
	public void updateCtx(ChannelHandlerContext ctx, String email) {
		UserInfo info = mapUsers.get(email);
		info.setCtx(ctx);
	}
}
