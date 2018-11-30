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
import com.boardgame.common.UserState;
import com.boardgame.model.GameRoom;
import com.boardgame.model.Room;
import com.boardgame.model.UserData;
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
import com.boardgame.request.RequestRoomPassword;
import com.boardgame.request.RequestRoomUsers;
import com.boardgame.request.RequestStart;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseConnectionRoom;
import com.boardgame.response.ResponseCreateRoom;
import com.boardgame.response.ResponseJoin;
import com.boardgame.response.ResponseLogin;
import com.boardgame.response.ResponseOutRoom;
import com.boardgame.response.ResponseReady;
import com.boardgame.response.ResponseRoomList;
import com.boardgame.response.ResponseRoomPassword;
import com.boardgame.response.ResponseRoomUsers;
import com.boardgame.response.ResponseStart;
import com.database.common.ResCode;
import com.database.dao.ScoreDao;
import com.database.dao.UserDao;
import com.database.model.User;
import com.database.util.CustomException;
import com.google.gson.Gson;
import com.security.Security;

import io.netty.buffer.Unpooled;
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
				List<Room> list = getController(gameNo).getRoomList(current, count);
	
				res = new ResponseRoomList(list, current, max);
				response(res, ctx);
			}
			break;
	
			case Common.IDENTIFIER_CREATE_ROOM:
			{
				RequestCreateRoom cr = gson.fromJson(result, RequestCreateRoom.class); 
	
				GameRoom room = new GameRoom(null, 
						cr.getTitle(), 
						cr.getMaxUser(), 
						cr.getEmail(),
						cr.getPassword(),
						cr.getNickName(),
						ctx);
	
				getController(gameNo).addRoom(room);
				res = new ResponseCreateRoom(room.getTitle(), room.getResUserList(), room.getNo());
				response(res, ctx);
			}
			break;
			case Common.IDENTIFIER_CONNECT_ROOM:
			{				
				RequestConnectionRoom cr = gson.fromJson(result, RequestConnectionRoom.class);
				Integer roomNo = cr.getRoomNo();
				//UserData user = UserController.Instance().getUser(cr.getEmail());
	
				try {
					//UserInfo info = new UserInfo(ctx, cr.getEmail(), cr.getNickName(), false, UserState.GAME_WAITING);
					UserInfo info = UserController.Instance().getUserInfo(cr.getEmail());
					String title = getController(gameNo).getRoom(roomNo).getTitle();
	
					List<UserData> userList = getController(gameNo).addUser(roomNo, info);
					res = new ResponseConnectionRoom(title, userList, roomNo);
	
					getController(gameNo).getRoom(roomNo).sendMessage(res);
					
				}catch(CustomException e) {
					res = new ResponseConnectionRoom(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_GAMING_USER :
			{
				RequestGamingUser req = gson.fromJson(result, RequestGamingUser.class);
				res = getController(gameNo).checkGaming(req.getEmail());	
				response(res, ctx);
			}
			break;
			case Common.IDENTIFIER_LOGIN :
			{
				RequestLogin req = gson.fromJson(result, RequestLogin.class);
				try {
//					String password = Security.Instance().deCryption(req.getPassword(), false);
	
//					System.out.println("password : " + req.getPassword());
//					System.out.println("password dec : " + password);
//	
					User user = userDao.selectUser(req.getEmail());//DBController.Instance().login(req.getEmail(), password);
	
					UserInfo info = new UserInfo(ctx, user.getEmail(), user.getNickname(), false, UserState.NONE);
					UserController.Instance().addUser(info);
	
					res = new ResponseLogin(req.isAutoLogin(), user.getEmail(), user.getNickname());
					response(res, ctx);
					
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e) {
	
					e.printStackTrace();
					res = new ResponseLogin(ResCode.ERROR_DECRYPTION.getResCode(), ResCode.ERROR_DECRYPTION.getMessage());
					response(res, ctx);
				}catch (ClassNotFoundException | SQLException e) {
					res = new ResponseLogin(ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());
					response(res, ctx);
				}catch (CustomException e) {
					res = new ResponseLogin(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_JOIN:
			{
				RequestJoin req = gson.fromJson(result, RequestJoin.class);
				String password;
	
				try {
//					password = Security.Instance().deCryption(req.getPassword(), false);
					User user = new User(req.getEmail(), password, req.getNickName(), req.getBirthday());
					userDao.insert(user);
					//DBController.Instance().join(user);
	
					res = new ResponseJoin(ResCode.SUCCESS.getResCode(), ResCode.SUCCESS.getMessage());
					response(res, ctx);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					res = new ResponseJoin(ResCode.ERROR_DB.getResCode(), ResCode.ERROR_DB.getMessage());
					response(res, ctx);
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseJoin(e.getResCode(), e.getMessage());
					response(res, ctx);
				}catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e1) {
	
					e1.printStackTrace();
					res = new ResponseJoin(ResCode.ERROR_DECRYPTION.getResCode(), ResCode.ERROR_DECRYPTION.getMessage());
					response(res, ctx);					
				}				
			}
			break;
			case Common.IDENTIFIER_READY :
			{
				RequestReady req = gson.fromJson(result, RequestReady.class);
				try {
					res = getController(gameNo).onReadyUser(req.getEmail(), req.isReady(), req.getRoomNo());
					getController(gameNo).getRoom(req.getRoomNo()).sendMessage(res);
					
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseReady(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_OUT_ROOM :
			{
				RequestOutRoom req = gson.fromJson(result, RequestOutRoom.class);
	
				try {
					getController(gameNo).onOutRoomUser(req.getOutUser(), req.getRoomNo());					
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseOutRoom(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
	
			case Common.IDENTIFIER_ROOM_USERS :
			{
				RequestRoomUsers req = gson.fromJson(result, RequestRoomUsers.class);
	
				try {
					res = new ResponseRoomUsers(getController(gameNo).getRoom(req.getRoomNo()).getResUserList());
					response(res, ctx);
				}catch(CustomException e) {
					e.printStackTrace();
					res = new ResponseRoomUsers(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_ROOM_PASSWORD :
			{
				RequestRoomPassword req = gson.fromJson(result, RequestRoomPassword.class);
				try {
					boolean isCheck = getController(gameNo).checkRoomPassword(req.getRoomNo(), req.getPassword());
					if(isCheck) {
						res = new ResponseRoomPassword(req.getRoomNo());
					}else {
						res = new ResponseRoomPassword(req.getRoomNo(), ResCode.ERROR_ROOM_PASSWORD.getMessage());
					}
					response(res, ctx);
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseRoomPassword(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_START:
			{
				RequestStart req = gson.fromJson(result, RequestStart.class);//(RequestStart)request;
				GameRoom room = null;
				try {
					room = getController(gameNo).getRoom(req.getRoomNo());
					room.checkStart();
	
					res = new ResponseStart();
					room.sendMessage(res);
					
				} catch (CustomException e) {
					e.printStackTrace();
					if(e.getResCode() == ResCode.ERROR_NOT_FOUND_ROOM.getResCode()) {
						res = new ResponseStart(e.getResCode(), e.getMessage());
						response(res, ctx);
					}else {
						ResponseRoomUsers resRoomUsers = new ResponseRoomUsers(room.getResUserList());
						for(UserInfo info : room.getUserList()) {
							if(info.isMaster()) {
								res = new ResponseStart(room.getResUserList(), e.getResCode(), e.getMessage());
								response(res, info.getCtx());
							}else {
								response(resRoomUsers, info.getCtx());
							}
						}
					}
	
				} 
	
			}
			break;
			default : {
				try {
					getController(gameNo).reqData(result, identifier, ctx);
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseBase(identifier, e.getResCode(), e.getMessage());
					response(res, ctx);
				}
				break;
			}				
		}		
	}

	public void response(ResponseBase res, ChannelHandlerContext ctx) {
		String resStr = getJson(res);
		System.out.println("res : " + resStr);
		ctx.write(Unpooled.copiedBuffer(resStr, CharsetUtil.UTF_8));
		ctx.flush();		
	}

	BaseController getController(int gameNo) {
		switch(gameNo) {
		case Common.GAME_DAVINCICODE :
			return DavinciCodeController.Instance();
		}

		return null;
	}

	public String getJson(ResponseBase res) {
		return gson.toJson(res);
	}
}
