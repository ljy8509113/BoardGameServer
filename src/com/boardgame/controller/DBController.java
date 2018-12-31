package com.boardgame.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.boardgame.common.UserState;
import com.boardgame.model.UserData;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseJoin;
import com.boardgame.response.ResponseLogin;
import com.database.common.ResCode;
import com.database.dao.GameDao;
import com.database.dao.ScoreDao;
import com.database.dao.UserDao;
import com.database.model.Game;
import com.database.model.User;
import com.database.util.CustomException;
import com.security.Security;

import io.netty.channel.ChannelHandlerContext;

public class DBController {
	private static DBController instance= null;

	public static DBController Instance() {
		if(instance == null) {
			instance = new DBController();
			
		}
		return instance;
	}	

	ScoreDao scoreDao = new ScoreDao();
	UserDao userDao = new UserDao();
	GameDao gameDao = new GameDao();
	
	public ResponseBase selectUser(String email, String password, ChannelHandlerContext ctx) {
		ResponseBase res;
		try {
			//				String password = Security.Instance().deCryption(req.password, false);
			String decPassword = Security.Instance().decrypt(password, false);
			System.out.println("password : " + password);
			System.out.println("password dec : " + decPassword);
			//
			User user = userDao.loginUser(email, decPassword);

			UserData info = new UserData(ctx, user.getEmail(), user.getNickname(), false, UserState.NONE);
			//				UserController.Instance().addUser(info);
			SocketController.Instance().connection(info);

			res = new ResponseLogin(user.getEmail(), user.getNickname());
			return res;

		}catch (ClassNotFoundException | SQLException e) {
			res = new ResponseLogin(ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());
			return res;
		}catch (CustomException e) {
			res = new ResponseLogin(e.getResCode(), e.getMessage());
			return res;
		}catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e1) {

			e1.printStackTrace();
			res = new ResponseJoin(ResCode.ERROR_DECRYPTION.getResCode(), ResCode.ERROR_DECRYPTION.getMessage());
			return res;					
		}
	}
	
	public ResponseBase addUser(String email, String nickName, String password, ChannelHandlerContext ctx) {
		ResponseBase res;
		try {
			String decPassword = Security.Instance().decrypt(password, false);
//				String password = Security.Instance().deCryption(req.getPassword(), false);
			if(email.equals("auto")) {
				//오토 
				int count = userDao.getAutoIdCount()+1;
				String autoEmail = com.database.common.Common.AUTO_ID + count;
				
				User user = new User(autoEmail, nickName, decPassword, true);
				userDao.insert(user, decPassword);
				res = new ResponseJoin(true, email, decPassword, nickName);
			}else {
				User user = new User(email, nickName, decPassword, false);
				userDao.insert(user, password);
				res = new ResponseJoin(false, email, password, nickName);
			}
			return res;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			res = new ResponseJoin(ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());
			return res;
		} catch (CustomException e) {
			e.printStackTrace();
			res = new ResponseJoin(e.getResCode(), e.getMessage());
			return res;
		}catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e1) {

			e1.printStackTrace();
			res = new ResponseJoin(ResCode.ERROR_DECRYPTION.getResCode(), ResCode.ERROR_DECRYPTION.getMessage());
			return res;					
		}
	}
	
	
	public List<Game> selectOnGames() {
		try {
			List<Game> gameList = gameDao.selectOnAll();
			return gameList;
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
