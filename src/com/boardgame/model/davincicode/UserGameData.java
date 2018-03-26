package com.boardgame.model.davincicode;

import java.util.ArrayList;

import com.boardgame.controller.game.DavinciCodeGame;

public class UserGameData {
	private int no;
	private String email;
	private String nickName;
	private ArrayList<NumberCard> cards;
	private boolean isLose = false;

	public UserGameData(int no, String email, String nickName) {
		this.no = no;
		this.email = email;
		this.nickName = nickName;		
	}

	public int getNo() {
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
}
