package com.boardgame.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.common.UserState;
import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.common.DavincicodeError;
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
import com.boardgame.response.ResponseGamingUser;
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
	private List<GameRoom> listRoom;
	
//	ScoreDao scoreDao = new ScoreDao();
//	UserDao userDao = new UserDao();
	
	private static RequestController instance= null;

	public static RequestController Instance() {
		if(instance == null) {
			instance = new RequestController();
			instance.listRoom = new ArrayList<GameRoom>();
		}
		return instance;
	}	

	public synchronized void reqData(String result, ChannelHandlerContext ctx) {
		RequestBase header = Common.gson.fromJson(result, RequestBase.class);
		String identifier = header.getIdentifier();
//		int gameNo = header.getGameNo();
//
//		switch(gameNo) {
//		case Common.GAME_DAVINCICODE:
//			DavinciCodeController.Instance().reqData(result, identifier, ctx);
//			break;
//		}
		
		
		ResponseBase res;
		switch(identifier) {
			case Common.IDENTIFIER_LOGIN :
			{
				RequestLogin req = Common.gson.fromJson(result, RequestLogin.class);
				res = DBController.Instance().selectUser(req.getEmail(), req.password, ctx);
				response(res, ctx);
			}
			break;
			case Common.IDENTIFIER_JOIN:
			{
				RequestJoin req = Common.gson.fromJson(result, RequestJoin.class);
				res = DBController.Instance().addUser(req.getEmail(), req.getNickName(), req.getPassword(), ctx);
				response(res, ctx);
								
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
				int max = listRoom.size();//getRoomMaxLength();
				List<Room> list = getRoomList(current, count);
	
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
	
				addRoom(room);
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
					GameRoom room = getRoom(roomNo);
					List<UserDataBase> userList;
					if( cr.isComputer() ) {
						userList = addComputer(room);
					}else {
						UserData info = SocketController.Instance().getUser(cr.getEmail());//UserController.Instance().getUserInfo(cr.getEmail());
						userList  = addUser(room, info);
					}
					
					String title = room.getTitle();
					res = new ResponseConnectionRoom(title, userList, roomNo, cr.getGameNo());
	
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
				res = checkGaming(req.getEmail());	
				response(res, ctx);
			}
			break;
			
			case Common.IDENTIFIER_READY :
			{
				RequestReady req = Common.gson.fromJson(result, RequestReady.class);
				try {
					res = onReadyUser(req.getEmail(), req.isReady(), req.getRoomNo());
					getRoom(req.getRoomNo()).sendMessage(res);
					
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
					onOutRoomUser(req.getOutUser(), req.getRoomNo());					
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
					GameRoom room = getRoom(req.getRoomNo());
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
					boolean isCheck = checkRoomPassword(req.getRoomNo(), req.getPassword());
					if(isCheck) {
						int roomNo = req.getRoomNo();
						GameRoom room = getRoom(roomNo);
						UserData info = SocketController.Instance().getUser(req.getEmail());//UserController.Instance().getUserInfo(cr.getEmail());
						String title = getRoom(roomNo).getTitle();
		
						List<UserDataBase> userList = addUser(room, info);
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
					room = getRoom(req.getRoomNo());
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
						GameRoom room = getRoom(roomNo);
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
	
	
	public List<UserData> addRoom(GameRoom room) {
		room.setNo( listRoom.size()+1 );
		listRoom.add(room);	

		return room.getUserList();		
	}

	public void removeRoom(int roomNo) {
		for(GameRoom room : listRoom) {
			if(room.getNo() == roomNo) {
				listRoom.remove(room);
				break;
			}
		}
	}
	
	public void removeRoom(GameRoom room) {
		listRoom.remove(room);
	}

	public List<UserDataBase> addUser(GameRoom room, UserData info) throws CustomException {
		boolean isAdd = false;
		isAdd = room.addUser(info);

		if(isAdd) {
			info.setState(UserState.GAME_WAITING);
			return room.getResUserList();
		}else {
			throw new CustomException(ResCode.ERROR_CONNECTION_ROOM.getResCode(), ResCode.ERROR_CONNECTION_ROOM.getMessage());
		}		
	}

	public List<UserDataBase> addComputer(GameRoom room) throws CustomException{
		if(room.addComputer()) {
			return room.getResUserList();
		}else {
			throw new CustomException(DavincicodeError.ERROR_ADD_COMPUTER.getCode(), DavincicodeError.ERROR_ADD_COMPUTER.getMessage());
		}
	}

	public List<Room> getRoomList(int current, int count){
		int endCount = current * count;

		if( endCount > listRoom.size()) {
			endCount = listRoom.size();
		}

		List<Room> resultList = new ArrayList<Room>();

		int startIndex = count * (current-1); 

		for(int i=startIndex; i<endCount; i++)
		{
			GameRoom room = listRoom.get(i);
			resultList.add(room.getRoom());
		}

		return resultList;
	}

	public int getRoomMaxLength() {
		return listRoom.size();
	}

	public ResponseGamingUser checkGaming(String email) {
		UserData info = SocketController.Instance().getUser(email);//UserController.Instance().getUserInfo(email);
		
		int roomNo = -1;
		boolean isGaming = false;
		
		if(info != null) {
			if(info.getState() == UserState.PLAING) {
				GameRoom room = findRoom(email);
				
				if(room == null) {
					//UserController.Instance().updateState(UserState.NONE, email);
					info.setState(UserState.NONE);
				}else {
					isGaming = true;
					roomNo = room.getNo();
				}
			}
		}
		
		ResponseGamingUser res = new ResponseGamingUser(isGaming, roomNo);
		return res;
	}

	public ResponseRoomInfo onReadyUser(String email, boolean isReady, int roomNo) throws CustomException {
		GameRoom room = getRoom(roomNo);

		if(isReady) {
			room.updateUserState(email, UserState.GAME_READY);
		}else {
			room.updateUserState(email, UserState.GAME_WAITING);
		}
		
//		ResponseReady res = new ResponseReady(email, isReady);
		ResponseRoomInfo res = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
		return res;
	}

	public void onOutRoomUser(String email, int roomNo) throws CustomException {
		GameRoom room = getRoom(roomNo);
		
		ResponseOutRoom res = new ResponseOutRoom();
		UserData info = room.getUser(email);
		RequestController.Instance().response(res, info.getCtx());
		
		if( room.getUser(email).isMaster() && room.getUserList().size() > 1) {
			room.removeUser(email);
			room.changeMaster(room.getUserList().get(0).email); //changeMaster(0);			
		}else {
			room.removeUser(email);
		}
		
		if(room.getUserList().size() == 0)
			removeRoom(room);
		else {
			ResponseRoomInfo resRoomUsers = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
			for(UserData i : room.getUserList()) {
				RequestController.Instance().response(resRoomUsers, i.getCtx());
			}
		}		
	}
	
	//전체 보내기
	public void sendMessage(ResponseBase res) {
		for(GameRoom room : listRoom) {
			List<UserData> userlist = room.getUserList();
			for(UserData info : userlist) {
//				info.sendMessage(res);
				RequestController.Instance().response(res, info.getCtx());
			}			
		}
	}

	//룸 유저에게만
	public void sendMessage(int roomNo, ResponseBase res) throws CustomException {
		GameRoom room = getRoom(roomNo);
		for(UserData info : room.getUserList()) {
			//info.sendMessage(res);
			RequestController.Instance().response(res, info.getCtx());
		}
	}	

	public GameRoom getRoom(int roomNo) throws CustomException {
		for(GameRoom i : listRoom) {
			if(i.getNo() == roomNo) {
				return i;				
			}				
		}		
		throw new CustomException(ResCode.ERROR_NOT_FOUND_ROOM.getResCode(), ResCode.ERROR_NOT_FOUND_ROOM.getMessage());
	}
	
	public GameRoom findRoom(String email) {
		for(GameRoom g : listRoom) {
			List<UserData> users = g.getUserList();
			for(UserData i : users) {
				if(i.getEmail().equals(email)) {
					return g;		
				}
			}
		}		
		return null;
	}
	
	public boolean checkRoomPassword(int roomNo, String password) throws CustomException {
		GameRoom room = getRoom(roomNo);
		
		return room.getPassword().equals(password);
	}
	

}
