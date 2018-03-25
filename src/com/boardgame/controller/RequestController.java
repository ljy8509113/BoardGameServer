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
import com.boardgame.model.UserInfo;
import com.boardgame.request.RequestBase;
import com.boardgame.request.RequestConnectionRoom;
import com.boardgame.request.RequestCreateRoom;
import com.boardgame.request.RequestGamingUser;
import com.boardgame.request.RequestJoin;
import com.boardgame.request.RequestLogin;
import com.boardgame.request.RequestOutRoom;
import com.boardgame.request.RequestReady;
import com.boardgame.request.RequestRoomList;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseConnectionRoom;
import com.boardgame.response.ResponseCreateRoom;
import com.boardgame.response.ResponseJoin;
import com.boardgame.response.ResponseLogin;
import com.boardgame.response.ResponseOutRoom;
import com.boardgame.response.ResponseReady;
import com.boardgame.response.ResponseRoomList;
import com.database.common.ResCode;
import com.database.dao.ScoreDao;
import com.database.dao.UserDao;
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
				int max = getController(gameNo).getRoomMaxLength();
				List<GameRoom.RoomInfo> list = getController(gameNo).getRoomList(current, count);
				
				res = new ResponseRoomList(list, current, max);
			}
			break;
			
			case Common.IDENTIFIER_CREATE_ROOM:
			{
				RequestCreateRoom cr = gson.fromJson(result, RequestCreateRoom.class); 
				
				GameRoom room = new GameRoom(null, 
						cr.getTitle(), 
						cr.getMaxUser(), 
						GameState.WAITING.getValue(), 
						cr.getEmail(),
						cr.getPassword(),
						cr.getNickName(),
						ctx); 
				getController(gameNo).addRoom(room);
				
				res = new ResponseCreateRoom(room.getTitle(), room.getResUserList());
				 
			}
			break;
			case Common.IDENTIFIER_CONNECT_ROOM:
			{				
				RequestConnectionRoom cr = gson.fromJson(result, RequestConnectionRoom.class);
				Integer roomNo = cr.getRoomId();
				
				try {
					UserInfo info = new UserInfo(ctx, cr.getEmail(), cr.getNickName(), false);
					String title = getController(gameNo).getRoom(roomNo).getTitle();
					List<UserInfo.User> userList = getController(gameNo).addUser(roomNo, info);
					res = new ResponseConnectionRoom(title, userList);
				}catch(CustomException e) {
					res = new ResponseConnectionRoom(e.getResCode(), e.getMessage());
				}
			}
			break;
			case Common.IDENTIFIER_GAMING_USER :
			{
				RequestGamingUser req = gson.fromJson(result, RequestGamingUser.class);
				res = getController(gameNo).checkGaming(req.getEmail());				
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
					res = new ResponseLogin(req.isAutoLogin(), user.getEmail(), user.getPassword(), user.getNickname());
					
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
			case Common.IDENTIFIER_READY :
			{
				RequestReady req = gson.fromJson(result, RequestReady.class);
				try {
					res = getController(gameNo).onReadyUser(req.getEmail(), req.isReady(), req.getRoomNo());
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseReady(e.getResCode(), e.getMessage());
				}
			}
				break;
			case Common.IDENTIFIER_OUT_ROOM :
			{
				RequestOutRoom req = gson.fromJson(result, RequestOutRoom.class);
				
				try {
					res = getController(gameNo).onOutRoomUser(req.getEmail(), req.getRoomNo());
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseOutRoom(e.getResCode(), e.getMessage());
				}
			}
				break;
			default : {
				try {
					getController(gameNo).reqData(header, identifier);
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseBase(identifier, e.getResCode(), e.getMessage());
				}
				break;
			}
							
		}
		
		if(res != null)
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
