package com.boardgame.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.boardgame.model.GameRoom;
import com.boardgame.response.ResponseGamingUser;
import com.database.common.Common;
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
		switch(room.getGameNo()) {
		case Common.DIVINCHICODE_GAME_CODE :
			DavinciCodeController.Instance().addRoom(room, ctx);
			break;
		}	
	}
	
	public List<GameRoom> getRoomList(int gameNo, int current, int count){
		List<GameRoom> list = new ArrayList<GameRoom>();
		switch(gameNo) {
		case Common.DIVINCHICODE_GAME_CODE :
			list = DavinciCodeController.Instance().getRoomList(current, count);
			break;
		}
		
		return list;
	}
	
	public int getRommMaxCount(int gameNo) {
		int max = 0;
		switch(gameNo) {
		case Common.DIVINCHICODE_GAME_CODE : 
			max = DavinciCodeController.Instance().getRoomMaxLength();
			break;
		}
		
		return max;
	}
	
	public ResponseGamingUser checkGaming(int gameNo, String email) {
		ResponseGamingUser res = null; 
		switch(gameNo) {
		case Common.DIVINCHICODE_GAME_CODE :
			res = DavinciCodeController.Instance().checkGaming(email, gameNo);
			break;
		}
		
		return res;
	}
	
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
