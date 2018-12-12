package com.boardgame.controller;

import java.util.HashMap;
import java.util.Map;

import com.boardgame.controller.game.DavinciCodeGame;
import com.boardgame.request.RequestBase;
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
	public void reqData(String req, String identifier, ChannelHandlerContext ctx){
		switch(identifier) {
//			case Common.IDENTIFIER_INIT_GAME :
//			{
//				RequestInitGame req = gson.fromJson(reqStr, RequestInitGame.class); //(RequestInitGame)request;
//				
//				GameRoom room = getRoom(req.getRoomNo());
//				
//				DavinciCodeGame game = new DavinciCodeGame(room);
//				map.put(room.getNo(), game);
//				
//				//ResponseGameCardInfo res = new ResponseGameCardInfo(game.setInitCard());
//				GameCardInfo cardInfo = game.setInitCard();
//				ResponseInitGame res = new ResponseInitGame(cardInfo);
//				room.sendMessage(res);			
//			}
//			break;
			default :
				super.reqData(req, identifier, ctx);
				break;
		}
	}
	
	
	
	
}
