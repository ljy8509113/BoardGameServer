package com.boardgame.response;

import java.util.ArrayList;

import com.boardgame.model.ResGameData;

public class ResponseGameList extends ResponseBase {
	ArrayList<ResGameData> gameList;
	public ResponseGameList(ArrayList<ResGameData> list) {
		gameList = list;
	}
}
