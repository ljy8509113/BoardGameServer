package com.boardgame.response;

import java.util.ArrayList;

import com.boardgame.common.Common;
import com.boardgame.model.ResGameData;
import com.database.common.ResCode;

public class ResponseGameList extends ResponseBase {
	ArrayList<ResGameData> gameList;
	
	public ResponseGameList(ArrayList<ResGameData> list) {
		super(Common.IDENTIFIER_GANE_LIST, ResCode.SUCCESS.getResCode());
		gameList = list;
	}
	
	public ResponseGameList(int resCode, String message ) {
		super(Common.IDENTIFIER_GANE_LIST, resCode, message);
	}
}
