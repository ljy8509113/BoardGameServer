package com.boardgame.controller;

import java.util.ArrayList;
import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.controller.game.DavinciCodeGame;
import com.boardgame.model.GameRoom;
import com.boardgame.request.RequestBase;
import com.boardgame.request.RequestStart;
import com.database.common.ResCode;
import com.database.util.CustomException;

public class DavinciCodeController extends BaseController {
	//game_id = 1
	//private GameService gameService;
	
	List<DavinciCodeGame> list;
	
	private static DavinciCodeController instance = null;
	public static DavinciCodeController Instance() {
		if(instance == null) {
			instance = new DavinciCodeController();
		}
		return instance;
	}
	
	public DavinciCodeController() {
		super();
		list = new ArrayList<>();
	}
	
	@Override 
	public void reqData(RequestBase request, String identifier) throws CustomException {
		switch(identifier) {
		case Common.IDENTIFIER_START:
		{
			RequestStart req = (RequestStart)request;
			try {
				GameRoom room = getRoom(req.getRoomNo());
				DavinciCodeGame game = new DavinciCodeGame(room);
				list.add(game);
				
			} catch (CustomException e) {
				e.printStackTrace();
				throw new CustomException(ResCode.ERROR_NOT_FOUND_ROOM.getResCode(), ResCode.ERROR_NOT_FOUND_ROOM.getMessage());
			} 
			
		}
			break;
		}
	}
	
	
	
	
}
