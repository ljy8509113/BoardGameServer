package com.boardgame.model.davincicode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameCardInfo {
	private List<UserGameData> arrayUser;
	private Map<Integer,NumberCard> mapFieldCards;

	public GameCardInfo() {		
		arrayUser = new ArrayList<UserGameData>();
		mapFieldCards = new HashMap<Integer, NumberCard>();
	}

	public void addUserData(UserGameData user) {
		arrayUser.add(user);
	}

	public List<UserGameData> getUserData(){
		return arrayUser;
	}

	public void addCard(int key, NumberCard card) {
		mapFieldCards.put(key, card);
	}

	public Map<Integer,NumberCard> getFieldCards(){
		return mapFieldCards;
	}

	public NumberCard getCard(int key) {
		return mapFieldCards.get(key);
	}

	public void moveCard(String email, int number) {
		NumberCard card = new NumberCard(number);
		UserGameData user = getUser(email);
		user.addCard(card);
		mapFieldCards.remove(number);
	}

	public UserGameData getUser(String email) {
		for(UserGameData data : arrayUser) {
			if(data.getEmail().equals(email)) {
				return data;				
			}
		}
		return null;
	}

	public boolean initCheck() {
		for(UserGameData data : arrayUser) {
			if( data.isInit() == false) {
				return false;
			}
		}
		return true;
	}

	public void sortUser() {
		Descending des = new Descending();
		Collections.sort(arrayUser, des);
	}

	// 내림차순
	class Descending implements Comparator<UserGameData> {
		@Override
		public int compare(UserGameData o1, UserGameData o2) {
			return o2.getNo().compareTo(o1.getNo());
		}
	}
}
