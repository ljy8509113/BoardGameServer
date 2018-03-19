package com.boardgame.controller;

import com.boardgame.service.GameService;

public class DavinciCodeController extends BaseController {
	//game_id = 1
	private GameService gameService;
	
	private static DavinciCodeController instance = null;
	public static DavinciCodeController Instance() {
		if(instance == null) {
			instance = new DavinciCodeController();
			instance.gameService = new GameService();
		}
		return instance;
	}
	
	public void reqData(String result, String identifier) {
		switch(identifier) {
		case "":
			break;
		}
	}
	
	
	
	
}
