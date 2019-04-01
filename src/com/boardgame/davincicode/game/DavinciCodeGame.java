package com.boardgame.davincicode.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.boardgame.common.Common;
import com.boardgame.common.UserType;
import com.boardgame.davincicode.common.DavinciCommon;
import com.boardgame.davincicode.common.DavincicodeError;
import com.boardgame.davincicode.model.NumberCard;
import com.boardgame.davincicode.model.UserGameData;
import com.boardgame.davincicode.request.RequestAttack;
import com.boardgame.davincicode.request.RequestInit;
import com.boardgame.davincicode.request.RequestSelectFieldCard;
import com.boardgame.davincicode.request.RequestSelectUserCard;
import com.boardgame.davincicode.response.ResponseAttack;
import com.boardgame.davincicode.response.ResponseFinish;
import com.boardgame.davincicode.response.ResponseGameCardInfo;
import com.boardgame.davincicode.response.ResponseSelectFieldCard;
import com.boardgame.davincicode.response.ResponseSelectUserCard;
import com.boardgame.davincicode.response.ResponseStartDavincicode;
import com.boardgame.davincicode.response.ResponseTurn;
import com.boardgame.game.BaseGame;
import com.boardgame.model.GameRoom;
import com.boardgame.model.UserData;
import com.boardgame.response.ResponseBase;

import io.netty.channel.ChannelHandlerContext;

public class DavinciCodeGame extends BaseGame{
	
	int turnUserIndex = 0; 
	int openWaitingCard = -1;
	
	final int JOCKER_START_INDEX = 24;
	
	public ArrayList<UserGameData> userList;
	public ArrayList<NumberCard> fieldCardList = new ArrayList<NumberCard>();
	DesNumber desNumber = new DesNumber();
	
	public DavinciCodeGame(GameRoom room) {
		super.room = room;	
		
		ArrayList<NumberCard> blackArray = new ArrayList<NumberCard>(); 
		ArrayList<NumberCard> whiteArray = new ArrayList<NumberCard>();
		
		for(int i=0; i<DavinciCommon.MAX_CARD_COUNT; i++) {
			NumberCard card = new NumberCard(i, false);
			
			if(i % 2 == 0) {
				blackArray.add(card);
			}else {
				whiteArray.add(card);
			}
		}
		
		Collections.shuffle(blackArray);
		Collections.shuffle(whiteArray);
		
		for(NumberCard c : blackArray) {
			fieldCardList.add(c);
		}
		
		for(NumberCard c : whiteArray) {
			fieldCardList.add(c);
		}
		
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
			boolean isSuccess = selectFieldCard(req.email, req.index); //cardInfo.selectFieldCard(req.getEmail(), req.index);
			
			if(isSuccess) {
				openWaitingCard = req.index;
				res = new ResponseSelectFieldCard(userList, fieldCardList, turnUserIndex, isSuccess, room.getNo(), req.index);
				room.sendMessage(res);
			}else {
				res = new ResponseSelectFieldCard(DavincicodeError.ALREAD_SELECTED.getCode(), DavincicodeError.ALREAD_SELECTED.getMessage(), userList, fieldCardList, req.index);
				room.sendMessage(res, ctx);
			}
		}
		break;
		case DavinciCommon.IDENTIFIER_SELECT_USER_CARD :
		{
//			RequestSelectUserCard req = Common.gson.fromJson(json, RequestSelectUserCard.class);
//			res = new ResponseSelectUserCard(userList, fieldCardList, turnUserIndex, req.selectIndex, req.selectUser, room.getNo());
//			room.sendMessage(res);
		}
			break;
		case DavinciCommon.IDENTIFIER_ATTACK:
		{
			RequestAttack req = Common.gson.fromJson(json, RequestAttack.class);
			
			UserGameData selectedUser = getUser(req.selectUser);
			NumberCard card = selectedUser.getCards().get(req.selectIndex);
			if(card.index == req.attackValue) {
				card.isOpen = true;
				boolean isLose = true;
				for(NumberCard c : selectedUser.getCards()) {
					if(c.isOpen == false) {
						isLose = false;
						break;
					}
				}				
				selectedUser.setLose(isLose);
				if(isLose) {
					boolean isFinish = true;
					for(UserGameData user : userList) {
						if(!req.email.equals(user.getEmail()) && !user.IsLose()) {
							isFinish = false;
						}
					}
					
					if(isFinish) {
						res = new ResponseFinish(userList, fieldCardList, turnUserIndex, req.roomNo); 
						room.sendMessage(res);
						return;
					}
				}				
			}else {
				UserGameData turnUser = userList.get(turnUserIndex);
				
				for(NumberCard c : turnUser.getCards()) {
					if(c.index == openWaitingCard) {
						c.isOpen = true;
						break;
					}
				}
			}
			
			res = new ResponseAttack(userList, fieldCardList, turnUserIndex, req.selectUser, req.selectIndex, req.attackValue, req.roomNo, openWaitingCard);
			room.sendMessage(res);
			openWaitingCard = -1;
		}
			break;
		case DavinciCommon.IDENTIFIER_GAME_CARD_INFO :
		{
			res = new ResponseGameCardInfo(userList, fieldCardList, turnUserIndex, room.getNo());
			room.sendMessage(res, ctx);
		}
			break;
		case DavinciCommon.IDENTIFIER_INIT_GAME :
		{
			RequestInit req = Common.gson.fromJson(json, RequestInit.class);
			UserGameData user = getUser(req.email);
			user.setInit(true);
			
			boolean isAllInit = true;
			
			for(UserGameData data : userList) {
				if(data.getUserType() != UserType.COMPUTER.getValue() && data.isInit() == false) {
					isAllInit = false;
				}
			}
			
			if(isAllInit) {
				for(UserGameData data : userList) {
					if(data.getUserType() == UserType.COMPUTER.getValue()) {
						int count = userList.size() > 3 ? 3:4;
						for(int i=0; i<count; i++) {
							NumberCard card = computerSelectFieldCard();
							//data.addCard(card);
							sortUserCard(data, card);
						}
						//Collections.sort(data.getCards(), desNumber);
						
					}
				}
				
				setTurn(turnUserIndex);				
			}			
		}
			break;
		case DavinciCommon.IDENTIFIER_NEXT :
		{
			setTurn(turnUserIndex + 1);
		}
			break;
		default :
			break;
		}
		
	}

	NumberCard computerSelectFieldCard() {
		Random r = new Random();
		int index = r.nextInt(fieldCardList.size());
		NumberCard selectCard = fieldCardList.get(index);
		NumberCard card = new NumberCard(selectCard.index, false);
		fieldCardList.remove(selectCard);
		return card;
	}
	
	@Override
	public void startGame() {
		userList = new ArrayList<UserGameData>();

		for(UserData user : room.getUserList()) {
			UserGameData data = new UserGameData(0, user.email, user.nickName, user.userType);
			userList.add(data);
		}
		
		Random r = new Random();
		int count = 0;
		
		while(count < userList.size()) {
			int no = r.nextInt(userList.size()) + 1;
			boolean isAdd = true;
			for(int i=0; i<userList.size(); i++) {
				if(userList.get(i).getNo() == no)
					isAdd = false;
			}
			if(isAdd) {
				userList.get(count).setNo(no);
				count++;
			}
		}
		
		UserComparator comp = new UserComparator();
		Collections.sort(userList, comp);
		
		ResponseStartDavincicode res = new ResponseStartDavincicode(userList, fieldCardList, room.getNo());
		room.sendMessage(res);
	}
	
	void setTurn(int nextTurn) {
		UserGameData user = null;
		int index = nextTurn;
		System.out.println("1. turn index: " + index);
		while( user == null ) {
			if(index == userList.size() )
				index = 0;
			
			if( userList.get(index).IsLose() ) {
				index+=1;				
			}else {
				user = userList.get(index);
				turnUserIndex = index;
			}				
		}
		System.out.println("2. turn index: " + turnUserIndex);
		ResponseTurn res = new ResponseTurn(userList, fieldCardList, turnUserIndex, room.getNo());
		room.sendMessage(res);
	}
	
	public boolean selectFieldCard(String email, int index) {
		for(NumberCard c : fieldCardList) {
			if(c.index == index) {
				fieldCardList.remove(c);
				NumberCard card = new NumberCard(index);
				UserGameData user = getUser(email);
				//user.addCard(card);
				sortUserCard(user, card);
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

	void sortUserCard(UserGameData user, NumberCard addCard) {
		boolean isJocker = false;
		
		for(NumberCard card : user.getCards()) {
			if(card.index >= JOCKER_START_INDEX) {
				isJocker = true;
			}
		}
		
		if(isJocker) {
			if(addCard.index >= JOCKER_START_INDEX) {
				addJocker(user.getCards(), addCard);
			}else {
				boolean isAdd = false;
				for(int i=0; i<user.getCards().size(); i++) {
					NumberCard c = user.getCards().get(i);
					
					if(c.index < JOCKER_START_INDEX && c.index > addCard.index) {
						user.getCards().add(i, addCard);
						isAdd = true;
						break;
					}
				}
				
				if(!isAdd) {
					user.getCards().add(addCard);
				}
			}
		}else {
			if(addCard.index >= JOCKER_START_INDEX) {
				addJocker(user.getCards(), addCard);
			}else {
				user.getCards().add(addCard);
				Collections.sort(user.getCards(), desNumber);
			}				
		}
	}
	
	void addJocker(ArrayList<NumberCard> cards, NumberCard addCard) {
		Random r = new Random();
		int addIndex = r.nextInt(cards.size());
		cards.add(addIndex, addCard);
	}
	
	// 내림차순
	class Descending implements Comparator<UserGameData> {
		@Override
		public int compare(UserGameData o1, UserGameData o2) {
			return o2.getNo().compareTo(o1.getNo());
		}
	}
	
	// 내림차순
	class DesNumber implements Comparator<NumberCard> {
		@Override
		public int compare(NumberCard o1, NumberCard o2) {
			return Integer.compare(o1.index, o2.index);
		}
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
