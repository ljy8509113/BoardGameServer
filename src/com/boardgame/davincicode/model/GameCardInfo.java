package com.boardgame.davincicode.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.boardgame.davincicode.common.DavinciCommon;

public class GameCardInfo {
	private ArrayList<UserGameData> userList;
//	private Map<Integer,NumberCard> mapFieldCards;
	public ArrayList<NumberCard> fieldCardList;
	
	public GameCardInfo() {		
		userList = new ArrayList<UserGameData>();
//		mapFieldCards = new HashMap<Integer, NumberCard>();
		fieldCardList = new ArrayList<NumberCard>();
		
		for(int i=0; i<DavinciCommon.MAX_CARD_COUNT; i++) {
			NumberCard card = new NumberCard(i);
			card.isJoker = false;
			card.isOpen = false;
			fieldCardList.add(card);
		}
		Collections.shuffle(fieldCardList);
	}

	public void addUserData(UserGameData user) {
		userList.add(user);
	}

	public ArrayList<UserGameData> getUserData(){
		return userList;
	}

//	public Map<Integer,NumberCard> getFieldCards(){
//		return mapFieldCards;
//	}
//
//	public NumberCard getCard(int key) {
//		return mapFieldCards.get(key);
//	}

	public boolean selectFieldCard(String email, int index) {
		for(NumberCard c : fieldCardList) {
			if(c.index == index) {
				NumberCard card = new NumberCard(index);
				fieldCardList.remove(c);
				UserGameData user = getUser(email);
				user.addCard(card);
				return true;
			}
		}
		
		return false;
	}

	public UserGameData getUser(String email) {
		for(UserGameData data : userList) {
			if(data.getEmail().equals(email)) {
				return data;				
			}
		}
		return null;
	}

//	public boolean initCheck() {
//		for(UserGameData data : userList) {
//			if( data.isInit() == false) {
//				return false;
//			}
//		}
//		return true;
//	}

	public void sortUser() {
		Descending des = new Descending();
		Collections.sort(userList, des);
	}

	// 내림차순
	class Descending implements Comparator<UserGameData> {
		@Override
		public int compare(UserGameData o1, UserGameData o2) {
			return o2.getNo().compareTo(o1.getNo());
		}
	}
}
