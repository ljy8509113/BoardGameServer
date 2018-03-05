package com.boardgame.controller;

import java.sql.SQLException;
import java.util.List;

import com.boardgame.model.GameRoom;
import com.boardgame.service.GameService;
import com.google.gson.Gson;

import io.netty.channel.ChannelHandlerContext;

public class GameController {

	private GameService gameService;
	

	private static GameController instance = null;
	public static GameController Instance() {
		if(instance == null) {
			instance = new GameController();
			instance.gameService = new GameService();
		}
		return instance;
	}

	public void createRoom(GameRoom room, ChannelHandlerContext ctx) throws ClassNotFoundException, SQLException {
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

	
}
