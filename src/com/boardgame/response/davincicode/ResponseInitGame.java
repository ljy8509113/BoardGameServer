package com.boardgame.response.davincicode;

import java.util.List;
import java.util.ArrayList;

import com.boardgame.common.Common;
import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.model.davincicode.NumberCard;
import com.boardgame.model.davincicode.UserGameData;
import com.boardgame.response.ResponseBase;
import com.database.common.ResCode;

public class ResponseInitGame extends ResponseBase {
	//GameCardInfo cardInfo;
	private List<UserGameData> arrayUser;
	private List<NumberCard> arrayFieldCards;

	
	public ResponseInitGame(GameCardInfo cardInfo) {
		super(Common.IDENTIFIER_INIT_GAME, ResCode.SUCCESS.getResCode());
		//this.cardInfo = cardInfo;
		arrayUser = cardInfo.getUserData();
		arrayFieldCards = new ArrayList<NumberCard>();
		for(int key : cardInfo.getFieldCards().keySet()) {
			NumberCard card = cardInfo.getFieldCards().get(key);
			arrayFieldCards.add(card);
		}
	}
}
