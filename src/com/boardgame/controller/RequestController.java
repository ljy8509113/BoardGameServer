package com.boardgame.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.boardgame.common.Common;
import com.boardgame.common.GameState;
import com.boardgame.model.GameRoom;
import com.boardgame.model.RoomUser;
import com.boardgame.model.UserInfo;
import com.boardgame.request.RequestBase;
import com.boardgame.request.RequestConnectionRoom;
import com.boardgame.request.RequestCreateRoom;
import com.boardgame.request.RequestGamingUser;
import com.boardgame.request.RequestJoin;
import com.boardgame.request.RequestLogin;
import com.boardgame.request.RequestRoomList;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseConnectionRoom;
import com.boardgame.response.ResponseCreateRoom;
import com.boardgame.response.ResponseJoin;
import com.boardgame.response.ResponseLogin;
import com.boardgame.response.ResponseRoomList;
import com.database.common.ResCode;
import com.database.dao.ScoreDao;
import com.database.dao.UserDao;
import com.database.model.Score;
import com.database.model.User;
import com.database.util.CustomException;
import com.google.gson.Gson;
import com.security.Security;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class RequestController {
	private Gson gson = new Gson();
	private static RequestController instance= null;
	
	public static RequestController Instance() {
		if(instance == null) {
			instance = new RequestController();
			instance.scoreDao = new ScoreDao();
			instance.userDao = new UserDao();
		}
		return instance;
	}
	
	ScoreDao scoreDao;
	UserDao userDao;
	
	public synchronized void reqData(String result, ChannelHandlerContext ctx) {
		RequestBase header = gson.fromJson(result, RequestBase.class);
		String identifier = header.getIdentifier();
		int gameNo = header.getGameNo();
		
		ResponseBase res = null;
		
		switch(identifier) {
			case Common.IDENTIFIER_ROOM_LIST:{
				RequestRoomList req = gson.fromJson(result, RequestRoomList.class);
				int current = req.getCurrent();
				int count = req.getCount();
				
				List<GameRoom> list = getController(gameNo).getRoomList(current, count);
				res = new ResponseRoomList(ResCode.SUCCESS.getResCode(), list, current, getController(gameNo).getRoomMaxLength());
			}
			break;
			
			case Common.IDENTIFIER_CREATE_ROOM:
			{
				RequestCreateRoom cr = gson.fromJson(result, RequestCreateRoom.class); 
				GameRoom room = new GameRoom(null, cr.getTitle(), cr.getGameNo(), cr.getMaxUser(), GameState.WAITING.getValue(), cr.getEmail(), cr.getNickName(), cr.getPassword());
			
				res = new ResponseCreateRoom(ResCode.SUCCESS.getResCode(), room.getTitle());
				getController(gameNo).addRoom(room, ctx);
				
//				RoomManager.Instance().addRoom(room);
//				Score score;
//				try {
//					
////					score = scoreDao.select(cr.getEmail(), cr.getGameNo()); //DBController.Instance().selectScore(cr.getEmail(), cr.getGameNo());
//					//, score.getTotal(), score.getWin(), score.getLose(), score.getDisconnect(), score.getPoint());
//				} catch (CustomException e) {
//					e.printStackTrace();
//					res = new ResponseCreateRoom(e.getResCode(), e.getMessage());//new ResponseBase(e.getResCode(), e.getMessage());
//				}				 
			}
			break;
			case Common.IDENTIFIER_CONNECT_ROOM:
			{				
				RequestConnectionRoom cr = gson.fromJson(result, RequestConnectionRoom.class);
				Integer roomNo = cr.getRoomId();
				
				try {
					
					Score score = scoreDao.select(cr.getEmail(), cr.getGameNo());//DBController.Instance().selectScore(cr.getEmail(), gameNo);
					RoomUser user = new RoomUser(cr.getEmail(), cr.getNickName(), false, score.getTotal(), score.getWin(), score.getLose(), score.getDisconnect());
					UserInfo info = new UserInfo(ctx, user);
					
					List<RoomUser> userList = getController(gameNo).addUser(roomNo, info);
					res = new ResponseConnectionRoom(ResCode.SUCCESS.getResCode(), userList);
				}catch(CustomException e) {
					res = new ResponseConnectionRoom(e.getResCode(), e.getMessage());
				}
			}
			break;
			case Common.IDENTIFIER_GAMING_USER :
			{
				RequestGamingUser req = gson.fromJson(result, RequestGamingUser.class);
				res = getController(gameNo).checkGaming(req.getEmail(), req.getGameNo());				
			}
			break;
			case Common.IDENTIFIER_LOGIN :
			{
				RequestLogin req = gson.fromJson(result, RequestLogin.class);
				try {
					String password = Security.Instance().deCryption(req.getPassword(), false);
					
					System.out.println("password : " + req.getPassword());
					System.out.println("password dec : " + password);
					
					User user = userDao.selectUser(req.getEmail(), password);//DBController.Instance().login(req.getEmail(), password);
					res = new ResponseLogin(ResCode.SUCCESS.getResCode(), req.isAutoLogin(), user.getEmail(), user.getPassword(), user.getNickname());
					
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e) {
					
					e.printStackTrace();
					res = new ResponseLogin(ResCode.ERROR_DECRYPTION.getResCode(), ResCode.ERROR_DECRYPTION.getMessage());
				}catch (ClassNotFoundException | SQLException e) {
					res = new ResponseLogin(ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());
				}catch (CustomException e) {
					res = new ResponseLogin(e.getResCode(), e.getMessage());
				}
			}
				break;
			case Common.IDENTIFIER_JOIN:
			{
				RequestJoin req = gson.fromJson(result, RequestJoin.class);
				String password;
				
				try {
					password = Security.Instance().deCryption(req.getPassword(), false);
					User user = new User(req.getEmail(), password, req.getNickName(), req.getBirthday());
					userDao.insert(user);
					//DBController.Instance().join(user);
					
					res = new ResponseJoin(ResCode.SUCCESS.getResCode(), ResCode.SUCCESS.getMessage());
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					res = new ResponseJoin(ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseJoin(e.getResCode(), e.getMessage());
				}catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e1) {
					
					res = new ResponseJoin(ResCode.ERROR_DECRYPTION.getResCode(), ResCode.ERROR_DECRYPTION.getMessage());
					e1.printStackTrace();
				}
			}
				break;			
		}
		
		response(gson.toJson(res), ctx);
		
	}

	void response(String res, ChannelHandlerContext ctx) {
		System.out.println("res : " + res);
		ChannelFuture future = ctx.write(Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
		ctx.flush();		
	}
	
	BaseController getController(int gameNo) {
		switch(gameNo) {
		case Common.GAME_DAVINCICODE :
			
			return DavinciCodeController.Instance();
		}
		
		return null;
	}
}
