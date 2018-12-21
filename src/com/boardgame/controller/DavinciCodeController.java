package com.boardgame.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.boardgame.common.Common;
import com.boardgame.common.UserState;
import com.boardgame.controller.game.DavinciCodeGame;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserData;
import com.boardgame.request.RequestStart;
import com.boardgame.request.davincicode.RequesetSelectNumber;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseRoomInfo;
import com.boardgame.response.ResponseStart;
import com.boardgame.response.davincicode.ResponseBaseDavinci;
import com.boardgame.response.davincicode.ResponseSelectNumber;
import com.database.common.ResCode;
import com.database.util.CustomException;
import com.google.gson.Gson;

import io.netty.channel.ChannelHandlerContext;

public class DavinciCodeController extends BaseController {
	//game_id = 1
	//private GameService gameService;
	
	Map<Integer, DavinciCodeGame> map;
	Gson gson = new Gson();
	private static DavinciCodeController instance = null;
	public static DavinciCodeController Instance() {
		if(instance == null) {
			instance = new DavinciCodeController();
		}
		return instance;
	}
	
	public DavinciCodeController() {
		super();
		map = new HashMap<Integer, DavinciCodeGame>();
	}
	
	public void disConnectionUser(ChannelHandlerContext ctx) {
		Iterator<Integer> keys = map.keySet().iterator();
		while( keys.hasNext() ){
			DavinciCodeGame game = map.get(keys.next());
			UserData data = game.room.getUser(ctx); 
			
			if(data != null) {
				if(game.room.isPlaing) {
					data.state = UserState.DISCONNECT;
					ResponseBaseDavinci res = new ResponseBaseDavinci(Common.IDENTIFIER_GAME_CARD_INFO, game.cardInfo);
					game.room.sendMessage(res);
				}else {
					game.room.removeUser(data.email);
					SocketController.Instance().mapUsers.remove(data.email);
					ResponseRoomInfo res = new ResponseRoomInfo(game.room.getResUserList(), game.room.getTitle());
					game.room.sendMessage(res);
				}
				
				return;
			}
		}
	}
	
	@Override 
	public void reqData(String reqStr, String identifier, ChannelHandlerContext ctx){
		ResponseBase res;
		switch(identifier) {
			case Common.IDENTIFIER_START:
			{
				RequestStart req = Common.gson.fromJson(reqStr, RequestStart.class);//(RequestStart)request;
				GameRoom room = null;
				try {
					room = getRoom(req.getRoomNo());
					room.checkStart();
					
					DavinciCodeGame game = new DavinciCodeGame(room);
					map.put(room.getNo(), game);
					room.isPlaing = true;
					res = new ResponseStart(game.cardInfo);
					room.sendMessage(res);
					
				} catch (CustomException e) {
					e.printStackTrace();
					if(e.getResCode() == ResCode.ERROR_NOT_FOUND_ROOM.getResCode()) {
						res = new ResponseStart(e.getResCode(), e.getMessage());
						response(res, ctx);
					}else {
						ResponseRoomInfo resRoomUsers = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
						for(UserData info : room.getUserList()) {
							if(info.isMaster) {
								res = new ResponseStart(e.getResCode(), e.getMessage());
								response(res, info.ctx);
							}else {
								response(resRoomUsers, info.ctx);
							}
						}
					}
				} 
			}
			break;
			case Common.IDENTIFIER_SELECT_NUMBER :
			{
				try {
					RequesetSelectNumber req = Common.gson.fromJson(reqStr, RequesetSelectNumber.class);
					DavinciCodeGame game = map.get(req.roomNo);
					
					boolean isSuccess = game.selectCard(req.getEmail(), req.index);
					res = new ResponseSelectNumber(req.index, req.getEmail(), isSuccess, game.cardInfo);
					if(isSuccess) {
						game.sendMessage(res);
					}else {
						response(res, ctx);
					}
				}catch(Exception e) {
					e.printStackTrace();
					res = new ResponseSelectNumber(ResCode.ERROR_NOT_FOUND_ROOM.getResCode(), ResCode.ERROR_NOT_FOUND_ROOM.getMessage());
					response(res, ctx);
				}
				
			}
				break;
			default :
				super.reqData(reqStr, identifier, ctx);
			break;
		}
	}
	
}
