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
import com.boardgame.model.RoomUserList;
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
import com.database.controller.DBController;
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
		if(instance == null)
			instance = new RequestController();
		return instance;
	}
	
	public synchronized void reqData(String result, ChannelHandlerContext ctx) {
		RequestBase header = gson.fromJson(result, RequestBase.class);
		String identifier = header.getIdentifier();
		String email = header.getEmail();
		
		ResponseBase res = null;
		
		switch(identifier) {
			case Common.IDENTIFIER_ROOM_LIST:{
				RequestRoomList req = gson.fromJson(result, RequestRoomList.class);
				int current = req.getCurrent();
				int count = req.getCount();
				//int max = RoomManager.Instance().getRoomMaxLength(req.getGameNo());
				
				//List<GameRoom> list = RoomManager.Instance().getRoomList(header.getGameNo()); //GameController.Instance().getRoomList(header.getGameNo(), ctx);
				List<GameRoom> list = GameController.Instance().getRoomList(header.getGameNo(), current, count); //RoomManager.Instance().getRoomList(req.getGameNo(), current, count);
				res = new ResponseRoomList(ResCode.SUCCESS.getResCode(), list, current, GameController.Instance().getRommMaxCount(header.getGameNo()));
			}
			break;
			
			case Common.IDENTIFIER_CREATE_ROOM:
			{
				RequestCreateRoom cr = gson.fromJson(result, RequestCreateRoom.class); 
				GameRoom room = new GameRoom(null, cr.getTitle(), cr.getGameNo(), cr.getMaxUser(), GameState.WAITING.getValue(), cr.getEmail(), cr.getNickName(), cr.getPassword());
			
//				RoomManager.Instance().addRoom(room);
				Score score;
				try {
					score = DBController.Instance().selectScore(cr.getEmail(), cr.getGameNo());
					res = new ResponseCreateRoom(ResCode.SUCCESS.getResCode(), room.getTitle(), score.getTotal(), score.getWin(), score.getLose(), score.getDisconnect());
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseCreateRoom(e.getResCode(), e.getMessage());//new ResponseBase(e.getResCode(), e.getMessage());
				}
				 
			}
			break;
			case Common.IDENTIFIER_CONNECT_ROOM:
			{
//				RequestConnectionRoom cr = gson.fromJson(result, RequestConnectionRoom.class);
//				Integer roomNo = cr.getRoomId();
//				Integer gameNo = cr.getGameNo();
////				GameRoom room = null;
//				
//				try {
//					UserInfo info = new UserInfo(ctx, email, cr.getNickName());
//					List<RoomUser> userList = RoomManager.Instance().addUser(gameNo, roomNo, info);
//					res = new ResponseConnectionRoom(ResCode.SUCCESS.getResCode(), userList);
//				} catch (CustomException e) {
//					res = new ResponseConnectionRoom(e.getResCode(), e.getMessage());					
//				}
			}
			break;
			case Common.IDENTIFIER_GAMING_USER :
			{
				RequestGamingUser req = gson.fromJson(result, RequestGamingUser.class);
				res = GameController.Instance().checkGaming(header.getGameNo(), req.getEmail()); //RoomManager.Instance().checkGaming(email, header.getGameNo());				
			}
			break;
			case Common.IDENTIFIER_LOGIN :
			{
				RequestLogin login = gson.fromJson(result, RequestLogin.class);
				try {
					String password = Security.Instance().deCryption(login.getPassword(), false);
					
					System.out.println("password : " + login.getPassword());
					System.out.println("password dec : " + password);
					
					User user = GameController.Instance().login(login.getEmail(), password);
					res = new ResponseLogin(ResCode.SUCCESS.getResCode(), login.isAutoLogin(), user.getEmail(), user.getPassword(), user.getNickname());
					
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
					GameController.Instance().join(req.getEmail(), password, req.getNickName(), req.getBirthday());
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
			case Common.IDENTIFIER_TEST :
			{
				
			}
				break;
		}
		
		response(gson.toJson(res), ctx);
		
		switch(header.getGameNo()) {
		case Common.GAME_DAVINCICODE :
			DavinciCodeController.Instance().reqData(result, identifier);
			break;
		}
	}

	void response(String res, ChannelHandlerContext ctx) {
		System.out.println("res : " + res);
		ChannelFuture future = ctx.write(Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
		ctx.flush();		
	}
}
