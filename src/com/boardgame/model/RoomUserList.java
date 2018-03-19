package com.boardgame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
			mapUsers.put(info.getUser().getEmail(), info);
	}

	public void removeUser(UserInfo info) {
		mapUsers.remove(info.getUser().getEmail());
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
	
	public boolean checkEmail(String email) {
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
	
	public Map<String, UserInfo> getMapUser(){
		return mapUsers;
	}
	
	public String getMasterEmail() {
		return masterEmail;
	}
	
	public List<RoomUser> getUserList(){
		List<RoomUser> list = new ArrayList<RoomUser>();
		
		for(String email : mapUsers.keySet()) {
			UserInfo info = mapUsers.get(email);
			list.add(info.getUser());
		}
		
		return list;
	}
}
