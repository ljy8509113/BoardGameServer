package com.boardgame.model;

import java.util.HashMap;
import java.util.Map;

import com.boardgame.common.ResCode;
import com.boardgame.util.CustomException;

import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class RoomUserList {
	int roomNo;
	Map<String, UserInfo> mapUsers = new HashMap<>();
	GameRoom room;

	public RoomUserList(GameRoom room) {
		this.room = room;
		this.roomNo = room.getNo();
	}

	public void addUser(UserInfo info) throws CustomException {
		if(room.getFullUser() <= mapUsers.size())
			throw new CustomException(ResCode.ERROR_FULL.getResCode(), ResCode.ERROR_FULL.getMessage());
		else 
			mapUsers.put(info.getUuid(), info);
	}

	public void removeUser(UserInfo info) {
		mapUsers.remove(info.getUuid());
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
			info.getCtx().write(Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
			info.getCtx().flush();
		}
	}
	
	public boolean checkUuid(String uuid) {
		UserInfo info = mapUsers.get(uuid);
		if(info == null)
			return false;
		else
			return true;
	}
}
