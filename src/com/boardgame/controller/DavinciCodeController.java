package com.boardgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.boardgame.common.Common;
import com.boardgame.controller.game.DavinciCodeGame;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserInfo;
import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.request.RequestStart;
import com.boardgame.request.davincicode.RequestInitGame;
import com.boardgame.response.ResponseRoomInfo;
import com.boardgame.response.ResponseStart;
import com.boardgame.response.davincicode.ResponseInitGame;
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
	public void reqData(String reqStr, String identifier, ChannelHandlerContext ctx) throws CustomException {
		switch(identifier) {
			case Common.IDENTIFIER_INIT_GAME :
			{
				RequestInitGame req = gson.fromJson(reqStr, RequestInitGame.class); //(RequestInitGame)request;
				
				GameRoom room = getRoom(req.getRoomNo());
				
				DavinciCodeGame game = new DavinciCodeGame(room);
				map.put(room.getNo(), game);
				
				//ResponseGameCardInfo res = new ResponseGameCardInfo(game.setInitCard());
				GameCardInfo cardInfo = game.setInitCard();
				ResponseInitGame res = new ResponseInitGame(cardInfo);
				room.sendMessage(res);			
			}
			break;
		}
	}
	
	
	
	
}
