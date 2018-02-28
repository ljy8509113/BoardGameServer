package com.boardgame.controller;

import java.sql.SQLException;
import java.util.List;

import com.boardgame.model.GameRoom;
import com.boardgame.model.GameUser;
import com.boardgame.service.GameService;

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
	
	public void createRoom(GameRoom room) {
		gameService.createRoom(room);
	}
	
	public List<GameRoom> getRoomList(Integer gameNo){
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
