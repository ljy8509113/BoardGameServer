package com.boardgame.controller.game;

import java.util.ArrayList;
import java.util.Random;

import com.boardgame.common.Common;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserData;
import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.model.davincicode.UserGameData;
import com.boardgame.request.RequestStart;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.davincicode.ResponseInit;
import com.boardgame.response.davincicode.ResponseOpenCard;
import com.boardgame.response.davincicode.ResponseTurn;

import io.netty.channel.ChannelHandlerContext;

public class DavinciCodeGame extends BaseGame{
	public GameCardInfo cardInfo;
	int currentTurn = 0; 

	public DavinciCodeGame(GameRoom room) {
		super.room = room;	
	}

	public boolean selectCard(String email, int index) {
		return cardInfo.moveCard(email, index);
	}

	public void openCard(String reqEmail, String targetEmail, int index, boolean isJoker) {
		UserGameData user = cardInfo.getUser(targetEmail);
		boolean isSuccess = user.openCard(index, isJoker);
		ResponseOpenCard res = new ResponseOpenCard(isSuccess, cardInfo.getUserData());//new ResponseOpenCard(targetEmail, index, isSuccess, reqEmail, user.IsLose());

		room.sendMessage(res);
	}

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
		
		case Common.IDENTIFIER_SELECT_NUMBER :
		{
//			try {
//				RequesetSelectNumber req = Common.gson.fromJson(reqStr, RequesetSelectNumber.class);
//				DavinciCodeGame game = map.get(req.roomNo);
//
//				boolean isSuccess = game.selectCard(req.getEmail(), req.index);
//				res = new ResponseSelectNumber(req.index, req.getEmail(), isSuccess, game.cardInfo);
//				if(isSuccess) {
//					game.sendMessage(res);
//				}else {
//					response(res, ctx);
//				}
//			}catch(Exception e) {
//				e.printStackTrace();
//				res = new ResponseSelectNumber(ResCode.ERROR_NOT_FOUND_ROOM.getResCode(), ResCode.ERROR_NOT_FOUND_ROOM.getMessage());
//				response(res, ctx);
//			}

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
		
		ResponseInit res = new ResponseInit(cardInfo);
		room.sendMessage(res);
	}
}
