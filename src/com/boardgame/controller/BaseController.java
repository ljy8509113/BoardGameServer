package com.boardgame.controller;

import java.io.Console;
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

import com.boardgame.common.Common;
import com.boardgame.common.UserState;
import com.boardgame.model.GameRoom;
import com.boardgame.model.Room;
import com.boardgame.model.UserData;
import com.boardgame.model.UserInfo;
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
import com.boardgame.response.ResponseGamingUser;
import com.boardgame.response.ResponseJoin;
import com.boardgame.response.ResponseLogin;
import com.boardgame.response.ResponseOutRoom;
import com.boardgame.response.ResponseRoomInfo;
import com.boardgame.response.ResponseRoomList;
import com.boardgame.response.ResponseRoomPassword;
import com.boardgame.response.ResponseStart;
import com.database.common.ResCode;
import com.database.dao.ScoreDao;
import com.database.dao.UserDao;
import com.database.model.User;
import com.database.util.CustomException;

import io.netty.channel.ChannelHandlerContext;

public class BaseController {
	private List<GameRoom> listRoom;
	
	ScoreDao scoreDao = new ScoreDao();
	UserDao userDao = new UserDao();
	
	public void reqData(String reqStr, String identifier, ChannelHandlerContext ctx){
		ResponseBase res;
		switch(identifier) {
			case Common.IDENTIFIER_ROOM_LIST:{
				RequestRoomList req = Common.gson.fromJson(reqStr, RequestRoomList.class);
				int current = req.getCurrent();
				int count = req.getCount();
				int max = getRoomMaxLength();
				List<Room> list = getRoomList(current, count);
	
				res = new ResponseRoomList(list, current, max);
				response(res, ctx);
			}
			break;
	
			case Common.IDENTIFIER_CREATE_ROOM:
			{
				RequestCreateRoom cr = Common.gson.fromJson(reqStr, RequestCreateRoom.class); 
	
				GameRoom room = new GameRoom(null, 
						cr.getTitle(), 
						cr.getMaxUser(), 
						cr.getEmail(),
						cr.getPassword(),
						cr.getNickName(),
						ctx);
	
				addRoom(room);
				res = new ResponseCreateRoom(room.getTitle(), room.getResUserList(), room.getNo());
				response(res, ctx);
			}
			break;
			case Common.IDENTIFIER_CONNECT_ROOM:
			{				
				RequestConnectionRoom cr = Common.gson.fromJson(reqStr, RequestConnectionRoom.class);
				Integer roomNo = cr.getRoomNo();
				//UserData user = UserController.Instance().getUser(cr.getEmail());
	
				try {
					//UserInfo info = new UserInfo(ctx, cr.getEmail(), cr.getNickName(), false, UserState.GAME_WAITING);
					UserInfo info = UserController.Instance().getUserInfo(cr.getEmail());
					String title = getRoom(roomNo).getTitle();
	
					List<UserData> userList = addUser(roomNo, info);
					res = new ResponseConnectionRoom(title, userList, roomNo);
	
					getRoom(roomNo).sendMessage(res);
					
				}catch(CustomException e) {
					res = new ResponseConnectionRoom(e.getResCode(), e.getMessage());
					response(res, ctx);
				}
			}
			break;
			case Common.IDENTIFIER_GAMING_USER :
			{
				RequestGamingUser req = Common.gson.fromJson(reqStr, RequestGamingUser.class);
				res = checkGaming(req.getEmail());	
				response(res, ctx);
			}
			break;
			case Common.IDENTIFIER_LOGIN :
			{
				RequestLogin req = Common.gson.fromJson(reqStr, RequestLogin.class);
				try {
	//				String password = Security.Instance().deCryption(req.getPassword(), false);
	
	//				System.out.println("password : " + req.getPassword());
	//				System.out.println("password dec : " + password);
	//
					User user = userDao.selectUser(req.getEmail());//DBController.Instance().login(req.getEmail(), password);
					
					UserInfo info = new UserInfo(ctx, user.getEmail(), user.getNickname(), false, UserState.NONE);
					UserController.Instance().addUser(info);
	
					res = new ResponseLogin(user.getEmail(), user.getNickname());
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
				RequestJoin req = Common.gson.fromJson(reqStr, RequestJoin.class);
				
				try {
	//				password = Security.Instance().deCryption(req.getPassword(), false);
					User user = new User(req.getEmail(), req.getNickName());
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
				RequestReady req = Common.gson.fromJson(reqStr, RequestReady.class);
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
				RequestOutRoom req = Common.gson.fromJson(reqStr, RequestOutRoom.class);
	
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
				RequestRoomInfo req = Common.gson.fromJson(reqStr, RequestRoomInfo.class);
	
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
				RequestRoomPassword req = Common.gson.fromJson(reqStr, RequestRoomPassword.class);
				try {
					boolean isCheck = checkRoomPassword(req.getRoomNo(), req.getPassword());
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
			
			
		}	
	}

	public void response(ResponseBase res, ChannelHandlerContext ctx) {
		
		RequestController.Instance().response(res, ctx);
	}
	
	public BaseController() {
		listRoom = new ArrayList<>();
	}

	public List<UserInfo> addRoom(GameRoom room) {
		room.setNo(makeRoomNo());
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

	public List<UserData> addUser(int roomNo, UserInfo info) throws CustomException {
		boolean isAdd = false;
		GameRoom room = getRoom(roomNo);

		isAdd = room.addUser(info);

		if(isAdd) {
			if(!UserController.Instance().updateState(UserState.GAME_WAITING, info.getEmail())) {
				throw new CustomException(ResCode.ERROR_EMAIL_NOT_FOUND.getResCode(), ResCode.ERROR_EMAIL_NOT_FOUND.getMessage());
			}
			return room.getResUserList();
		}else {
			throw new CustomException(ResCode.ERROR_CONNECTION_ROOM.getResCode(), ResCode.ERROR_CONNECTION_ROOM.getMessage());
		}		
	}

	public int makeRoomNo() {		
		if(listRoom.size() == 0)
			return 1;
		else
			return listRoom.get(listRoom.size()-1).getNo() + 1;		
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
		UserInfo info = UserController.Instance().getUserInfo(email);
		
		int roomNo = -1;
		boolean isGaming = false;
		
		if(info != null) {
			if(info.getState() == UserState.PLAING.getValue()) {
				Integer no = findRoomNo(email);
				
				if(no == null) {
					UserController.Instance().updateState(UserState.NONE, email);
				}else {
					isGaming = true;
					roomNo = no;
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
		UserInfo info = room.getUser(email);
		RequestController.Instance().response(res, info.getCtx());
		
		if( room.getUser(email).isMaster() && room.getUserList().size() > 1) {
			room.removeUser(email);
			room.changeMaster(0);			
		}else {
			room.removeUser(email);
		}
		
		if(room.getUserList().size() == 0)
			removeRoom(room);
		else {
			ResponseRoomInfo resRoomUsers = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
			for(UserInfo i : room.getUserList()) {
				RequestController.Instance().response(resRoomUsers, i.getCtx());
			}
		}		
	}
	
	//전체 보내기
	public void sendMessage(ResponseBase res) {
		for(GameRoom room : listRoom) {
			List<UserInfo> userlist = room.getUserList();
			for(UserInfo info : userlist) {
//				info.sendMessage(res);
				RequestController.Instance().response(res, info.getCtx());
			}			
		}
	}

	//룸 유저에게만
	public void sendMessage(int roomNo, ResponseBase res) throws CustomException {
		GameRoom room = getRoom(roomNo);
		for(UserInfo info : room.getUserList()) {
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
	
	public Integer findRoomNo(String email) {
		for(GameRoom g : listRoom) {
			List<UserInfo> users = g.getUserList();
			for(UserInfo i : users) {
				if(i.getEmail().equals(email)) {
					return g.getNo();		
				}
			}
		}		
		return null;
	}
	
	public boolean checkRoomPassword(int roomNo, String password) throws CustomException {
		GameRoom room = getRoom(roomNo);
		
		return room.getPassword().equals(password);
	}
	
	public void startGame(int roomNo) throws CustomException {
		GameRoom room = getRoom(roomNo);
	}
}
