package com.boardgame.service;

import java.sql.SQLException;
import java.util.List;

import com.boardgame.dao.GameRoomDao;
import com.boardgame.model.GameRoom;

public class GameService {
	private GameRoomDao gameDao;
	
	public GameService() {
		gameDao = new GameRoomDao();
	}
	
	public List<GameRoom> getRoomList(Integer gameNo) throws ClassNotFoundException, SQLException{
		return gameDao.selectAll(gameNo);
	}
	
	public Integer createRoom(GameRoom room) throws ClassNotFoundException, SQLException {
		return gameDao.insertRoom(room);
	}
	
	public GameRoom getRoom(Integer gameNo, Integer roomId) throws ClassNotFoundException, SQLException {
		return gameDao.select(gameNo, roomId);
	}
}
