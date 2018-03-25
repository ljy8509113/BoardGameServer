package com.boardgame.response.davincicode;

import java.util.List;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.UserGameData;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseGameSituation extends ResponseBase{
	List<UserGameData> userData;
	public ResponseGameSituation(List<UserGameData> userData) {
		super(Common.IDENTIFIER_GAME_SITUATION, ResCode.SUCCESS.getResCode());
		this.userData = userData;
	}
	
	public ResponseGameSituation(int resCode, String message) {
		super(Common.IDENTIFIER_GAME_SITUATION, resCode, message);
	}
}
