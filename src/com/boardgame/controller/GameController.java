package com.boardgame.controller;

import java.sql.SQLException;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.GameRoom;
import com.boardgame.request.RequestBase;
import com.boardgame.response.ResponseRoomList;
import com.boardgame.service.GameService;
import com.google.gson.Gson;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class GameController {

	private GameService gameService;
	private Gson gson = new Gson();

	private static GameController instance = null;
	public static GameController Instance() {
		if(instance == null) {
			instance = new GameController();
			instance.gameService = new GameService();
		}
		return instance;
	}

	public void createRoom(GameRoom room, ChannelHandlerContext ctx) {
		gameService.createRoom(room);
	}

	public List<GameRoom> getRoomList(Integer gameNo, ChannelHandlerContext ctx){
		List<GameRoom> list = null;
		try {
			list = gameService.getRoomList(gameNo);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}		
		return list;
	}

	public void reqData(StringBuffer buffer, ChannelHandlerContext ctx) {
		RequestBase header = gson.fromJson(buffer.toString(), RequestBase.class);
		String identifier = header.identifier;
		String uuid = header.uuid;
		
		switch(identifier) {
		case "game_room_list":
			List<GameRoom> list = getRoomList(header.gameNo, ctx);
			
			ResponseRoomList res = new ResponseRoomList(identifier, "0", list);
			String json = gson.toJson(res);
			response(json, ctx);

			break;
		case "create_room":
			
			GameRoom room = new GameRoom();
			
			
			break;
		}

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
