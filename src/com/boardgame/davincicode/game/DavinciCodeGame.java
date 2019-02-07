package com.boardgame.davincicode.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.boardgame.common.Common;
import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.common.DavincicodeError;
import com.boardgame.davincicode.model.GameCardInfo;
import com.boardgame.davincicode.model.UserGameData;
import com.boardgame.davincicode.request.RequestSelectFieldCard;
import com.boardgame.davincicode.response.ResponseGameCardInfo;
import com.boardgame.davincicode.response.ResponseOpenCard;
import com.boardgame.davincicode.response.ResponseSelectFieldCard;
import com.boardgame.davincicode.response.ResponseStartDavincicode;
import com.boardgame.davincicode.response.ResponseTurn;
import com.boardgame.game.BaseGame;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserData;
import com.boardgame.response.ResponseBase;

import io.netty.channel.ChannelHandlerContext;

public class DavinciCodeGame extends BaseGame{
	public GameCardInfo cardInfo;
	int currentTurn = 0; 

	public DavinciCodeGame(GameRoom room) {
		super.room = room;	
	}

//	public boolean selectCard(String email, int index) {
//		return cardInfo.moveCard(email, index);
//	}

//	public void openCard(String reqEmail, String targetEmail, int index, boolean isJoker) {
//		UserGameData user = cardInfo.getUser(targetEmail);
//		boolean isSuccess = user.openCard(index, isJoker);
//		ResponseOpenCard res = new ResponseOpenCard(isSuccess, cardInfo.getUserData());//new ResponseOpenCard(targetEmail, index, isSuccess, reqEmail, user.IsLose());
//
//		room.sendMessage(res);
//	}

	public void nextTurn(int currentNo, UserGameData user) {
		int nextNo = currentNo++;
		if(nextNo > cardInfo.getUserData().size()) {
			nextNo = 1;
		}

		ResponseTurn res = new ResponseTurn(nextNo);
		room.sendMessage(res);

	}
	
	public void disConnection(String email) {
		room.getUser(email);
	}
	
	public void sendMessage(ResponseBase res) {
		room.sendMessage(res);
	}
	

	@Override
	public void setData(String identifier, String json, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		ResponseBase res;
		switch(identifier) {
		
		case DavinciCommon.IDENTIFIER_SELECT_FIELD_CARD :
		{
			RequestSelectFieldCard req = Common.gson.fromJson(json, RequestSelectFieldCard.class);
			boolean isSuccess = cardInfo.selectFieldCard(req.getEmail(), req.index);
			
			if(isSuccess) {
				res = new ResponseSelectFieldCard(cardInfo, req.getEmail(), isSuccess, room.getNo());
				room.sendMessage(res);
			}else {
				res = new ResponseSelectFieldCard(DavincicodeError.ALREAD_SELECTED.getCode(), DavincicodeError.ALREAD_SELECTED.getMessage());
				room.sendMessage(res, ctx);
			}
		}
		break;
		case DavinciCommon.IDENTIFIER_SELECT_USER_CARD :
		{
			
		}
			break;
		case DavinciCommon.IDENTIFIER_GAME_CARD_INFO :
		{
			res = new ResponseGameCardInfo(cardInfo);
			room.sendMessage(res, ctx);
		}
			break;
		default :
			break;
		}
		
	}

	@Override
	public void startGame() {
		cardInfo = new GameCardInfo();
		for(UserData user : room.getUserList()) {
			UserGameData data = new UserGameData(0, user.email, user.nickName);
			cardInfo.addUserData(data);
		}
		
		Random r = new Random();
		int count = 0;
		ArrayList<UserGameData> users = cardInfo.getUserData();
		while(count >= users.size()) {
			int no = r.nextInt(users.size()) + 1;
			boolean isAdd = true;
			for(int i=0; i<users.size(); i++) {
				if(users.get(i).getNo() == no)
					isAdd = false;
			}
			if(isAdd) {
				users.get(count).setNo(no);
				count++;
			}
		}
		
		UserComparator comp = new UserComparator();
		Collections.sort(users, comp);
		
		ResponseStartDavincicode res = new ResponseStartDavincicode(cardInfo, room.getNo());
		room.sendMessage(res);
	}
	
	class UserComparator implements Comparator<UserGameData> {
		@Override
		public int compare(UserGameData first, UserGameData second) {
			int fValue = first.getNo();
			int sValue = second.getNo();
			
			if(fValue > sValue) {
				return 1;
			}else if(fValue < sValue) {
				return -1;
			}else {
				return 0;
			}
		}
	}
}
