package com.boardgame.davincicode.model;

import java.util.ArrayList;
import java.util.Comparator;

import com.boardgame.davincicode.game.DavinciCodeGame;

public class UserGameData {
	private Integer no;
	private String email;
	private String nickName;
	private ArrayList<NumberCard> cards;
	private boolean isLose = false;
	boolean isInit = false;
	int userType;
//	Descending descending;
	
	public UserGameData(int no, String email, String nickName, int type) {
		this.no = no;
		this.email = email;
		this.nickName = nickName;	
		cards = new ArrayList<>();
		this.userType = type;
				
//		descending = new Descending();
	}

	public Integer getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public ArrayList<NumberCard> getCards() {
		return cards;
	}
	public void setCards(ArrayList<NumberCard> cards) {
		this.cards = cards;
	}	
	public boolean IsLose() {
		return isLose;
	}

	public void setLose(boolean isLose) {
		this.isLose = isLose;
	}
	
	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int type) {
		this.userType = type;
	}

	public void addCard(NumberCard card) {
		cards.add(card);
//		Collections.sort(cards, descending);
		
	}
}
