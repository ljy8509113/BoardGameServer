package com.boardgame.controller.game;

import java.util.ArrayList;
import java.util.Random;

import com.boardgame.model.GameRoom;
import com.boardgame.model.UserData;
import com.boardgame.model.davincicode.GameCardInfo;
import com.boardgame.model.davincicode.NumberCard;
import com.boardgame.model.davincicode.UserGameData;
import com.boardgame.response.ResponseBase;
import com.boardgame.response.davincicode.ResponseOpenCard;
import com.boardgame.response.davincicode.ResponseTurn;

public class DavinciCodeGame {
	public GameRoom room;
	public GameCardInfo cardInfo;
//	public static final int CARD_MAX_INDEX = 26;
//	public static final int CARD_COUNT = 4;
//	public static final int JOKER_NUMBER = 99;
	int currentTurn = 0; 

	public DavinciCodeGame(GameRoom room) {
		this.room = room;	
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
		
		
	}
	
//	public GameCardInfo setInitCard() {
//		arrayUser = new ArrayList<>();
		//		mapCard = new HashMap<>();
		
//		cardInfo = new GameCardInfo();
//		int jokerNumber = JOKER_NUMBER;
//		for(int i=0; i<CARD_MAX_INDEX; i++) {
//			NumberCard n;
//			if(i >= 24) {
//				n = new NumberCard(jokerNumber);
//				jokerNumber++;
//			}else {
//				n = new NumberCard(i+1);
//			}
//			//mapCard.put(n.getNumber(), n);
//			cardInfo.addCard(n.getNumber(), n);
//		}
//
//		//순서 정하기
//		ArrayList<Integer> noArray = new ArrayList<>();
//		int maxCount = room.getUserList().size();
//
//		Random r = new Random();
//		while(noArray.size() < maxCount) {
//			int number = r.nextInt(maxCount) + 1;
//			if(noArray.size() == 0) {
//				noArray.add(number);
//			}else {
//				boolean isAdd = true;
//				for(int n : noArray) {
//					if(number == n) {
//						isAdd = false;
//						break;
//					}
//				}
//				if(isAdd) {
//					noArray.add(number);
//				}
//			}
//		}
//
//		for(int i=0; i<room.getUserList().size(); i++) {
//			UserInfo user = room.getUserList().get(i);
//			UserGameData data = new UserGameData(noArray.get(i), user.getEmail(), user.getNickName());
//			//arrayUser.add(data);
//			cardInfo.addUserData(data);
//		}
//		
//		cardInfo.sortUser();
//
//		return cardInfo;
//	}

//	public void initSelect(String email, int index) throws CustomException {
		
//		if(selectNumber(email, number)) {
//			if(cardInfo.initCheck()) {
//				ResponseTurn res = new ResponseTurn(1);
//				room.sendMessage(res);
//			}else {
//				//				ResponseInitNumber res = new ResponseInitNumber(email, number);
//				ResponseGameCardInfo res = new ResponseGameCardInfo(cardInfo);
//				room.sendMessage(res);				
//			}	
//		}else {
//			//throw new CustomException(ResCode.ERROR_NUMBER_NOT_SELECT.getResCode(), ResCode.ERROR_NUMBER_NOT_SELECT.getMessage());
//			ResponseGameCardInfo res = new ResponseGameCardInfo(ResCode.ERROR_NUMBER_NOT_SELECT.getResCode(), ResCode.ERROR_NUMBER_NOT_SELECT.getMessage(), cardInfo);
//			room.sendMessage(res);
//		}
//	}

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
}
