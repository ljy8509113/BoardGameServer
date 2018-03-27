package com.boardgame.model.davincicode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.boardgame.controller.game.DavinciCodeGame;

public class UserGameData {
	private Integer no;
	private String email;
	private String nickName;
	private ArrayList<NumberCard> cards;
	private boolean isLose = false;
	Descending descending;
	
	public UserGameData(int no, String email, String nickName) {
		this.no = no;
		this.email = email;
		this.nickName = nickName;	
		cards = new ArrayList<>();
		descending = new Descending();
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

	public void addCard(NumberCard card) {
		cards.add(card);
		Collections.sort(cards, descending);
		
	}

	public boolean isInit() {
		return cards.size() == DavinciCodeGame.CARD_COUNT ? true : false;
	}

	public boolean checkNumber(int index, int number) {
		NumberCard card = cards.get(index);
		boolean isSuccess = false;

		if(number >= DavinciCodeGame.JOKER_NUMBER) {
			if(card.getNumber() >= number) {
				card.setOpen(true);
				isSuccess = true;
			}
		}else {
			if(card.getNumber() == number) {
				card.setOpen(true);
				isSuccess = true;
			}
		}
		
		if(isSuccess) {
			boolean isLose = true;
			for(NumberCard c : cards) {
				if(c.IsOpen() == false) {
					isLose = false;
					break;
				}
			}

			if(isLose)
				this.isLose = true;
		}

		return isSuccess;
	}
	
	// 내림차순
	class Descending implements Comparator<NumberCard> {
	    @Override
	    public int compare(NumberCard o1, NumberCard o2) {
	        return o2.getNumber().compareTo(o1.getNumber());
	    }
	}

}
