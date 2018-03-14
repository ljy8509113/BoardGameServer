package com.boardgame.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.boardgame.model.GameRoom;
import com.database.controller.DBController;
import com.database.model.User;

import io.netty.channel.ChannelHandlerContext;

public class GameController {
//	private GameService gameService;
//	private UserService userService;
	
	private static GameController instance = null;
	public static GameController Instance() {
		if(instance == null) {
			instance = new GameController();
//			instance.gameService = new GameService();
//			instance.userService = new UserService();
		}
		return instance;
	}

	public void createRoom(GameRoom room, ChannelHandlerContext ctx) throws Exception {
		RoomManager.Instance().addRoom(room);		
	}

//	public List<GameRoom> getRoomList(Integer gameNo, ChannelHandlerContext ctx){
//		List<GameRoom> list = null;
//		try {
//			list = gameService.getRoomList(gameNo);
//		}catch(ClassNotFoundException e) {
//			e.printStackTrace();
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}		
//		return list;
//	}

//	public GameRoom getRoom(Integer gameNo, Integer roomId) throws ClassNotFoundException, SQLException {
//		GameRoom room = null;
//		room = gameService.getRoom(gameNo, roomId);
//		
//		return room;
//	}
	
	public void join(String email, String password, String nickName, Date birthday) throws ClassNotFoundException, SQLException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, com.database.util.CustomException {
		User user = new User(email, password, nickName, birthday);
		//userService.join(user);
		DBController.Instance().join(user);
	}
	
	public User login(String email, String password) throws InvalidKeyException, ClassNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, SQLException, com.database.util.CustomException {
		//return userService.login(email, password);
		return DBController.Instance().login(email, password);
	}
}
