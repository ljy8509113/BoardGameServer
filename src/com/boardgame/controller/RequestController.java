package com.boardgame.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.boardgame.common.Common;
import com.boardgame.common.GameState;
import com.boardgame.common.ResCode;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserInfo;
import com.boardgame.request.RequestBase;
import com.boardgame.request.RequestConnectionRoom;
import com.boardgame.request.RequestCreateRoom;
import com.boardgame.request.RequestJoin;
import com.boardgame.request.RequestLogin;
import com.boardgame.request.RequestRoomList;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseCreateRoom;
import com.boardgame.response.ResponseJoin;
import com.boardgame.response.ResponseLogin;
import com.boardgame.response.ResponseRoomList;
import com.boardgame.util.CustomException;
import com.boardgame.util.DBUtil;
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
	
	public synchronized void reqData(StringBuffer buffer, ChannelHandlerContext ctx) {
		RequestBase header = gson.fromJson(buffer.toString(), RequestBase.class);
		String identifier = header.getIdentifier();
		String uuid = header.getUuid();
		
		ResponseBase res = null;
		
		switch(identifier) {
			case Common.IDENTIFIER_ROOM_LIST:{
				RequestRoomList req = gson.fromJson(buffer.toString(), RequestRoomList.class);
				int current = req.getCurrent();
				int count = req.getCount();
				int max = RoomManager.Instance().getRoomMaxLength(req.getGameNo());
				
				//List<GameRoom> list = RoomManager.Instance().getRoomList(header.getGameNo()); //GameController.Instance().getRoomList(header.getGameNo(), ctx);
				List<GameRoom> list = RoomManager.Instance().getRoomList(req.getGameNo(), current, count);
				res = new ResponseRoomList(ResCode.SUCCESS.getResCode(), list, current, max);
			}
			break;
			
			case Common.IDENTIFIER_CREATE_ROOM:
			{
				RequestCreateRoom cr = gson.fromJson(buffer.toString(), RequestCreateRoom.class); 
				GameRoom room = new GameRoom(null, cr.getTitle(), cr.getGameNo(), cr.getMaxUser(), GameState.WAITING.getValue(), cr.getUuid());
			
				//GameController.Instance().createRoom(room, ctx);
				RoomManager.Instance().addRoom(room);
				res = new ResponseCreateRoom(ResCode.SUCCESS.getResCode(), room.getTitle()); 
			}
			break;
			case Common.IDENTIFIER_CONNECT_ROOM:
			{
				RequestConnectionRoom cr = gson.fromJson(buffer.toString(), RequestConnectionRoom.class);
				Integer roomNo = cr.getRoomId();
				Integer gameNo = cr.getGameNo();
//				GameRoom room = null;
				
				try {
					UserInfo info = new UserInfo(ctx, uuid);
					RoomManager.Instance().addUser(gameNo, roomNo, info); //GameController.Instance().getRoom(gameNo, roomId);
					res = new ResponseBase(ResCode.SUCCESS.getResCode(), ResCode.SUCCESS.getMessage());
				} catch (CustomException e) {
					res = new ResponseBase(e.getResCode(), e.getMessage());					
				}
			}
			break;
			case Common.IDENTIFIER_GAMING_USER :
			{
				res = RoomManager.Instance().checkGaming(uuid, header.getGameNo());				
			}
			break;
			case Common.IDENTIFIER_LOGIN :
			{
				RequestLogin login = gson.fromJson(buffer.toString(), RequestLogin.class);
				try {
					String password = Security.Instance().deCryptionClient(login.getPassword());
					
					System.out.println("password : " + login.getPassword());
					System.out.println("password dec : " + password);
					
					GameController.Instance().login(login.getEmail(), password);
					
					res = new ResponseLogin(ResCode.SUCCESS.getResCode(), login.isAutoLogin(), login.getEmail(), login.getPassword(), login.getNickName());
					
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
				RequestJoin req = gson.fromJson(buffer.toString(), RequestJoin.class);
				String password;
				
				try {
					password = Security.Instance().deCryptionClient(req.getPassword());
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
			DavinciCodeController.Instance().reqData(buffer, identifier);
			break;
		}
	}

	void response(String res, ChannelHandlerContext ctx) {
		System.out.println("res : " + res);
		ChannelFuture future = ctx.write(Unpooled.copiedBuffer(res, CharsetUtil.UTF_8));
		ctx.flush();		
	}
}