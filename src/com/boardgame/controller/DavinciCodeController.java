package com.boardgame.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boardgame.common.Common;
import com.boardgame.controller.game.DavinciCodeGame;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserInfo;
import com.boardgame.request.RequestBase;
import com.boardgame.request.RequestStart;
import com.boardgame.request.davincicode.RequestInitGame;
import com.boardgame.response.ResponseRoomUsers;
import com.boardgame.response.ResponseStart;
import com.boardgame.response.davincicode.ResponseGameCardInfo;
import com.database.common.ResCode;
import com.database.util.CustomException;

import io.netty.channel.ChannelHandlerContext;

public class DavinciCodeController extends BaseController {
	//game_id = 1
	//private GameService gameService;
	
	Map<Integer, DavinciCodeGame> map;
	
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
	public void reqData(RequestBase request, String identifier, ChannelHandlerContext ctx) throws CustomException {
		switch(identifier) {
		case Common.IDENTIFIER_START:
		{
			RequestStart req = (RequestStart)request;
			GameRoom room = null;
			try {
				room = getRoom(req.getRoomNo());
				room.checkStart();
				
				ResponseStart res = new ResponseStart();
				room.sendMessage(res);
				
			} catch (CustomException e) {
				e.printStackTrace();
				if(e.getResCode() == ResCode.ERROR_NOT_FOUND_ROOM.getResCode()) {
					ResponseStart res = new ResponseStart(e.getResCode(), e.getMessage());
					RequestController.Instance().response(res, ctx);
				}else {
					ResponseRoomUsers resRoomUsers = new ResponseRoomUsers(room.getResUserList());
					for(UserInfo info : room.getUserList()) {
						if(info.isMaster()) {
							ResponseStart res = new ResponseStart(room.getResUserList(), e.getResCode(), e.getMessage());
							RequestController.Instance().response(res, info.getCtx());
						}else {
							RequestController.Instance().response(resRoomUsers, info.getCtx());
						}
					}
				}
				
			} 
			
		}
			break;
			
		case Common.IDENTIFIER_INIT_GAME :
		{
			RequestInitGame req = (RequestInitGame)request;
			
			GameRoom room = getRoom(req.getRoomNo());
			
			DavinciCodeGame game = new DavinciCodeGame(room);
			map.put(room.getNo(), game);
			
			ResponseGameCardInfo res = new ResponseGameCardInfo(game.setInitCard());
			room.sendMessage(res);			
		}
			break;
		}
	}
	
	
	
	
}
