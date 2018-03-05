package com.boardgame.controller;

import java.sql.SQLException;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.common.GameState;
import com.boardgame.model.GameRoom;
import com.boardgame.request.RequestBase;
import com.boardgame.request.RequestCreateRoom;
import com.boardgame.response.ResponseBase;
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
		String identifier = header.identifier;
		String uuid = header.uuid;
		
		String json = null;
		ResponseBase res;
		
		switch(identifier) {
			case "game_room_list":{
				List<GameRoom> list = GameController.Instance().getRoomList(header.gameNo, ctx);
				
				res = new ResponseRoomList(identifier, "0", list);
				json = gson.toJson(res);
				
			}
				break;
			case "create_room":
			{
				RequestCreateRoom cr = gson.fromJson(buffer.toString(), RequestCreateRoom.class); 
				GameRoom room = new GameRoom(null, cr.title, cr.gameNo, cr.fullUser, GameState.WAITING.getValue(), cr.uuid);
			
				try {
					GameController.Instance().createRoom(room, ctx);
					res = new ResponseCreateRoom(identifier, "0", room.getTitle());
					json = gson.toJson(res);
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
				break;
				
			case "connection":
			{
				
			}
		}

		
		response(json, ctx);
		
		switch(header.gameNo) {
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
