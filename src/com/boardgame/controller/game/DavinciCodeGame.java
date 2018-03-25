package com.boardgame.controller.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.boardgame.model.GameRoom;
import com.boardgame.model.UserInfo;
import com.boardgame.model.davincicode.NumberCard;
import com.boardgame.model.davincicode.UserGameData;
import com.boardgame.response.davincicode.ResponseInitNumber;

public class DavinciCodeGame {
	GameRoom room;
	List<UserGameData> arrayUser;
	Map<Integer,NumberCard> mapCard;
	public static final int CARD_MAX_INDEX = 26;
	public static final int CARD_COUNT = 4;
	int currentTurn = 0; 
	
	public DavinciCodeGame(GameRoom room) {
		this.room = room;
		arrayUser = new ArrayList<>();
		mapCard = new HashMap<>();
		
		int jokerNumber = 99;
		for(int i=0; i<CARD_MAX_INDEX; i++) {
			NumberCard n;
			if(i >= 24) {
				n = new NumberCard(jokerNumber, true);
				jokerNumber++;
			}else {
				n = new NumberCard(i+1, false);
			}
			mapCard.put(n.getNumber(), n);
		}
		
		ArrayList<Integer> noArray = new ArrayList<>();
		int maxCount = room.getUserList().size();
		
		Random r = new Random();
		while(noArray.size() < maxCount) {
			int number = r.nextInt(maxCount) + 1;
			if(noArray.size() == 0) {
				noArray.add(number);
			}else {
				boolean isAdd = true;
				for(int n : noArray) {
					if(number == n) {
						isAdd = false;
						break;
					}
				}
				if(isAdd) {
					noArray.add(number);
				}
			}
		}
		
		for(int i=0; i<room.getUserList().size(); i++) {
			UserInfo info = room.getUserList().get(i);
			UserGameData data = new UserGameData(noArray.get(i), info.getEmail(), info.getNickName());
			arrayUser.add(data);
		}
	}
	
	UserGameData getUser(String email) {
		for(UserGameData data : arrayUser) {
			if(data.getEmail().equals(email)) {
				return data;				
			}
		}
		return null;
	}
	
	public void initSelect(String email, int number) {
		boolean isInit = true;
		
		if(selectNumber(email, number)) {
			for(UserGameData data : arrayUser) {
				if( data.isInit() == false) {
					isInit = false;
					break;
				}
			}
			
			if(isInit) {
				
			}else {
				ResponseInitNumber res = new ResponseInitNumber(email, number);
				room.sendMessage(res);				
			}	
		}else {
			
		}
	}
	
	public boolean selectNumber(String email, int number) {
		NumberCard nc = mapCard.get(number);
		if(nc != null) {
			NumberCard card = new NumberCard(nc.getNumber(), nc.IsJoker());
			UserGameData user = getUser(email);
			user.addCard(card);
			return true;
		}else {
			return false;
		}
	}
	
}
