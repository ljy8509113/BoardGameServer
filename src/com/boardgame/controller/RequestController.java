package com.boardgame.controller;

import java.sql.SQLException;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.common.GameState;
import com.boardgame.common.ResCode;
import com.boardgame.model.GameRoom;
import com.boardgame.request.RequestBase;
import com.boardgame.request.RequestConnectionRoom;
import com.boardgame.request.RequestCreateRoom;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseConnectionRoom;
import com.boardgame.response.ResponseCreateRoom;
import com.boardgame.response.ResponseRoomList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class RequestController {
	private Gson gson = new Gson();
	private static RequestController instance= null;
	
	public static RequestController Instance() {
		if(instance == null)
			instance = new RequestController();
		return instance;
	}
	
	public synchronized void reqData(StringBuffer buffer, ChannelHandlerContext ctx) {
		RequestBase header = gson.fromJson(buffer.toString(), RequestBase.class);
		String identifier = header.getIdentifier();
		String uuid = header.getUuid();
		
		ResponseBase res = null;
		
		switch(identifier) {
			case Common.IDENTIFIER_ROOM_LIST:{
				List<GameRoom> list = GameController.Instance().getRoomList(header.getGameNo(), ctx);
				res = new ResponseRoomList(Common.IDENTIFIER_ROOM_LIST, ResCode.SUCCESS.getResCode(), list);
			}
			break;
			case Common.IDENTIFIER_CREATE_ROOM:
			{
				RequestCreateRoom cr = gson.fromJson(buffer.toString(), RequestCreateRoom.class); 
				GameRoom room = new GameRoom(null, cr.getTitle(), cr.getGameNo(), cr.getFullUser(), GameState.WAITING.getValue(), cr.getUuid());
			
				try {
					GameController.Instance().createRoom(room, ctx);
					res = new ResponseCreateRoom(Common.IDENTIFIER_CREATE_ROOM, ResCode.SUCCESS.getResCode(), room.getTitle());
				} catch (ClassNotFoundException | SQLException e) {
					res = new ResponseBase(Common.IDENTIFIER_CREATE_ROOM, ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());
				} catch(Exception e) {
					res = new ResponseBase(Common.IDENTIFIER_CREATE_ROOM, ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());
				}
			}
			break;
			case Common.IDENTIFIER_CONNECT_ROOM:
			{
				RequestConnectionRoom cr = gson.fromJson(buffer.toString(), RequestConnectionRoom.class);
				Integer roomId = cr.getRoomId();
				Integer gameNo = cr.getGameNo();
				GameRoom room = null;
				
				try {
					room = GameController.Instance().getRoom(gameNo, roomId);
					
					if(room == null) {
						res = new ResponseBase(Common.IDENTIFIER_CREATE_ROOM, ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());						
					}else if(room.getCurrent() == room.getFullUser()) {
						res = new ResponseBase(Common.IDENTIFIER_CREATE_ROOM, ResCode.ERROR_FULL.getResCode(), ResCode.ERROR_FULL.getMessage());
					}else if(room.getState().equals(GameState.WAITING.getValue()) == false) {
						res = new ResponseBase(Common.IDENTIFIER_CREATE_ROOM, ResCode.ERROR_FULL.getResCode(), ResCode.ERROR_FULL.getMessage());
					}else {
						res = new ResponseConnectionRoom(Common.IDENTIFIER_CONNECT_ROOM, ResCode.SUCCESS.getResCode());
					}
					
				} catch (ClassNotFoundException | SQLException e) {
					res = new ResponseBase(Common.IDENTIFIER_CREATE_ROOM, ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());					
				}
			}
		}
		
		response(gson.toJson(res), ctx);
		
		switch(header.getGameNo()) {
		case Common.GAME_DAVINCICODE :
			DavinciCodeController.Instance().reqData(buffer, identifier);
			break;
		}
	}

	void response(String res, ChannelHandlerContext ctx) {
		System.out.println("res : " + res);
		ctx.write(Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
		ctx.flush();		
	}
}
