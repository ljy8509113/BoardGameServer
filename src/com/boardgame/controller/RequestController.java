package com.boardgame.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.response.ResponseStartDavincicode;
import com.boardgame.model.GameRoom;
import com.boardgame.model.ResGameData;
import com.boardgame.model.Room;
import com.boardgame.model.UserData;
import com.boardgame.model.UserDataBase;
import com.boardgame.request.RequestBase;
import com.boardgame.request.RequestConnectionRoom;
import com.boardgame.request.RequestCreateRoom;
import com.boardgame.request.RequestGamingUser;
import com.boardgame.request.RequestJoin;
import com.boardgame.request.RequestLogin;
import com.boardgame.request.RequestOutRoom;
import com.boardgame.request.RequestReady;
import com.boardgame.request.RequestRoomInfo;
import com.boardgame.request.RequestRoomList;
import com.boardgame.request.RequestRoomPassword;
import com.boardgame.request.RequestStart;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseConnectionRoom;
import com.boardgame.response.ResponseCreateRoom;
import com.boardgame.response.ResponseGameList;
import com.boardgame.response.ResponseJoin;
import com.boardgame.response.ResponseLogin;
import com.boardgame.response.ResponseOutRoom;
import com.boardgame.response.ResponseRoomInfo;
import com.boardgame.response.ResponseRoomList;
import com.boardgame.response.ResponseRoomPassword;
import com.database.common.ResCode;
import com.database.model.Game;
import com.database.util.CustomException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class RequestController {
//	private List<GameRoom> listRoom;
	
//	ScoreDao scoreDao = new ScoreDao();
//	UserDao userDao = new UserDao();
	
	private static RequestController instance= null;

	public static RequestController Instance() {
		if(instance == null) {
			instance = new RequestController();
//			instance.listRoom = new ArrayList<GameRoom>();
		}
		return instance;
	}	

	public synchronized void reqData(String result, ChannelHandlerContext ctx) {
		RequestBase header = Common.gson.fromJson(result, RequestBase.class);
		String identifier = header.getIdentifier();
		
		ResponseBase res;
		switch(identifier) {
			case Common.IDENTIFIER_LOGIN :
			{
				RequestLogin req = Common.gson.fromJson(result, RequestLogin.class);
//				res = DBController.Instance().selectUser(req.getEmail(), req.password, ctx);
//				response(res, ctx);
				
				UserData info = DBController.Instance().selectUser(req.getEmail(), req.password, ctx);
				if(info != null) {
					SocketController.Instance().connection(info);
					res = new ResponseLogin(info.getEmail(), info.getNickName());
					response(res, ctx);
				}				
			}
			break;
			case Common.IDENTIFIER_JOIN:
			{
				RequestJoin req = Common.gson.fromJson(result, RequestJoin.class);
				boolean isAdd = DBController.Instance().addUser(req.getEmail(), req.getNickName(), req.getPassword(), ctx);
				if(isAdd) {
					res = new ResponseJoin(false, req.getEmail(), req.getPassword(), req.getNickName());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_GANE_LIST :
			{
//				ResponseGameList req = Common.gson.fromJson(result, ResponseGameList.class);
				List<Game> list = DBController.Instance().selectOnGames();
				ArrayList<ResGameData> listData = new ArrayList<>();
				for(Game g : list) {
					ResGameData data = new ResGameData(g.getTitle(), g.getGameNo());
					listData.add(data);
				}
				
				res = new ResponseGameList(listData);
				response(res, ctx);
			}
				break;
			case Common.IDENTIFIER_ROOM_LIST:{
				RequestRoomList req = Common.gson.fromJson(result, RequestRoomList.class);
				int current = req.getCurrent();
				int count = req.getCount();
				int max = RoomController.Instance().getRoomSize();//getRoomMaxLength();
				List<Room> list = RoomController.Instance().getRoomList(current, count);
	
				res = new ResponseRoomList(list, current, max);
				response(res, ctx);
			}
			break;
	
			case Common.IDENTIFIER_CREATE_ROOM:
			{
				RequestCreateRoom cr = Common.gson.fromJson(result, RequestCreateRoom.class); 
	
				GameRoom room = new GameRoom(null, 
						cr.getTitle(), 
						cr.getEmail(),
						cr.getPassword(),
						cr.getNickName(),
						ctx,
						cr.getGameNo());
	
				RoomController.Instance().addRoom(room);
				res = new ResponseCreateRoom(room.getTitle(), room.getResUserList(), room.getNo(), cr.getGameNo());
				response(res, ctx);
			}
			break;
			case Common.IDENTIFIER_CONNECT_ROOM:
			{				
				RequestConnectionRoom cr = Common.gson.fromJson(result, RequestConnectionRoom.class);
				Integer roomNo = cr.getRoomNo();
				//UserData user = UserController.Instance().getUser(cr.getEmail());
	
				try {
					//UserInfo info = new UserInfo(ctx, cr.getEmail(), cr.getNickName(), false, UserState.GAME_WAITING);
					GameRoom room = RoomController.Instance().getRoom(roomNo);
					List<UserDataBase> userList;
					if( cr.isComputer() ) {
						userList = RoomController.Instance().addComputer(room);
					}else {
						UserData info = SocketController.Instance().getUser(cr.getEmail());//UserController.Instance().getUserInfo(cr.getEmail());
						userList  = RoomController.Instance().addUser(room, info);
					}
					
					String title = room.getTitle();
					res = new ResponseConnectionRoom(title, userList, roomNo, room.getGameNo());
	
					room.sendMessage(res);
					
				}catch(CustomException e) {
					res = new ResponseConnectionRoom(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_GAMING_USER :
			{
				RequestGamingUser req = Common.gson.fromJson(result, RequestGamingUser.class);
				res = RoomController.Instance().checkGaming(req.getEmail());	
				response(res, ctx);
			}
			break;
			
			case Common.IDENTIFIER_READY :
			{
				RequestReady req = Common.gson.fromJson(result, RequestReady.class);
				try {
					res = RoomController.Instance().onReadyUser(req.getEmail(), req.isReady(), req.getRoomNo());
					RoomController.Instance().getRoom(req.getRoomNo()).sendMessage(res);
					
				} catch (CustomException e) {
					e.printStackTrace();
	//				res = new ResponseReady(e.getResCode(), e.getMessage());
					res = new ResponseRoomInfo(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_OUT_ROOM :
			{
				RequestOutRoom req = Common.gson.fromJson(result, RequestOutRoom.class);
	
				try {
					RoomController.Instance().onOutRoomUser(req.getOutUser(), req.getRoomNo());					
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseOutRoom(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
	
			case Common.IDENTIFIER_ROOM_INFO:
			{
				RequestRoomInfo req = Common.gson.fromJson(result, RequestRoomInfo.class);
	
				try {
					GameRoom room = RoomController.Instance().getRoom(req.getRoomNo());
					res = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
					response(res, ctx);
				}catch(CustomException e) {
					e.printStackTrace();
					res = new ResponseRoomInfo(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_ROOM_PASSWORD :
			{
				RequestRoomPassword req = Common.gson.fromJson(result, RequestRoomPassword.class);
				try {
					boolean isCheck = RoomController.Instance().checkRoomPassword(req.getRoomNo(), req.getPassword());
					if(isCheck) {
						int roomNo = req.getRoomNo();
						GameRoom room = RoomController.Instance().getRoom(roomNo);
						UserData info = SocketController.Instance().getUser(req.getEmail());//UserController.Instance().getUserInfo(cr.getEmail());
						String title = RoomController.Instance().getRoom(roomNo).getTitle();
		
						List<UserDataBase> userList = RoomController.Instance().addUser(room, info);
						res = new ResponseConnectionRoom(title, userList, roomNo, req.getGameNo());
						room.sendMessage(res);
//						res = new ResponseRoomPassword(req.getRoomNo(), title, userList);
					}else {
						res = new ResponseRoomPassword(req.getRoomNo(), ResCode.ERROR_ROOM_PASSWORD.getMessage());
						response(res, ctx);
					}
				} catch (CustomException e) {
					e.printStackTrace();
					res = new ResponseRoomPassword(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_START:
			{
				RequestStart req = Common.gson.fromJson(result, RequestStart.class);//(RequestStart)request;
				GameRoom room = null;
				try {
					room = RoomController.Instance().getRoom(req.getRoomNo());//getRoom(req.getRoomNo());
					room.checkStart();
					room.startGame();
					
				} catch (CustomException e) {
					e.printStackTrace();
					if(e.getResCode() == ResCode.ERROR_NOT_FOUND_ROOM.getResCode()) {
						res = new ResponseStartDavincicode(e.getResCode(), e.getMessage());
						response(res, ctx);
					}else {
						ResponseRoomInfo resRoomUsers = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
						for(UserData info : room.getUserList()) {
							if(info.isMaster()) {
								res = new ResponseStartDavincicode(e.getResCode(), e.getMessage());
								response(res, info.ctx);
							}else {
								response(resRoomUsers, info.ctx);
							}
						}
					}
				} 
			}
				break;
			default:
			{
				int gameNo = header.getGameNo();
				int roomNo = header.getRoomNo();
				try {
					switch(gameNo) {
					case DavinciCommon.GAME_DAVINCICODE :
					{
						GameRoom room = RoomController.Instance().getRoom(roomNo);//getRoom(roomNo);
						room.updateData(identifier, result, ctx);
					}
						break;
					}
				} catch (CustomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				break;
		}
		
	}

	public void response(ResponseBase res, ChannelHandlerContext ctx) {
		String resStr = Common.gson.toJson(res);//getJson(res);
//		byte [] mesBytes = resStr.getBytes();
//		
//		short size = (short)mesBytes.length;
//		
//		byte[] sizeBytes = new byte[2];
//		
//		for(int i=0; i<sizeBytes.length && i<Short.SIZE; i++) {
//			sizeBytes[i] = (byte)((size >> (8*i)) & 0xff);
//		}
//		
//		int resSize = sizeBytes.length + mesBytes.length;
//		byte [] resBytes = new byte[resSize];
//		
//		System.arraycopy(sizeBytes, 0, resBytes, 0, sizeBytes.length);
//		System.arraycopy(mesBytes, 0, resBytes, sizeBytes.length, mesBytes.length);
//		ByteBuf buf = Unpooled.wrappedBuffer(resBytes);
//		
		if(ctx != null) {
			System.out.println( "res : " + resStr );
			ByteBuf buf = Unpooled.copiedBuffer(resStr, CharsetUtil.UTF_8);
			ctx.write(buf);
			ctx.flush();
		}else {
			System.out.println("ctx null");
		}
				
	}
	
}
