package com.boardgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.boardgame.common.Common;
import com.boardgame.controller.game.DavinciCodeGame;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserInfo;
import com.boardgame.request.RequestStart;
import com.boardgame.request.davincicode.RequesetSelectNumber;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.ResponseRoomInfo;
import com.boardgame.response.ResponseStart;
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
					
					res = new ResponseStart(game.cardInfo.getUserData(), Common.MAX_CARD_COUNT);
					room.sendMessage(res);
					
				} catch (CustomException e) {
					e.printStackTrace();
					if(e.getResCode() == ResCode.ERROR_NOT_FOUND_ROOM.getResCode()) {
						res = new ResponseStart(e.getResCode(), e.getMessage());
						response(res, ctx);
					}else {
						ResponseRoomInfo resRoomUsers = new ResponseRoomInfo(room.getResUserList(), room.getTitle());
						for(UserInfo info : room.getUserList()) {
							if(info.isMaster()) {
								res = new ResponseStart(e.getResCode(), e.getMessage());
								response(res, info.getCtx());
							}else {
								response(resRoomUsers, info.getCtx());
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
					
					if(isSuccess) {
						res = new ResponseSelectNumber(req.index, req.getEmail(), isSuccess);
						game.sendMessage(res);
					}else {
						res = new ResponseSelectNumber(req.index, req.getEmail(), isSuccess);
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
