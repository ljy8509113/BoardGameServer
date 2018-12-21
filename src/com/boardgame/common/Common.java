package com.boardgame.common;

import com.google.gson.Gson;

public class Common {
	public static final int GAME_DAVINCICODE = 1;

	public static final String IDENTIFIER_CONNECT_ROOM 		= "connection_room";
	public static final String IDENTIFIER_ROOM_LIST 		= "game_room_list";
	public static final String IDENTIFIER_CREATE_ROOM 		= "create_room";
	public static final String IDENTIFIER_GAMING_USER 		= "gaming_user";
	public static final String IDENTIFIER_LOGIN 			= "login";
	public static final String IDENTIFIER_USER_INFO 		= "user_info";
	public static final String IDENTIFIER_JOIN 				= "join";
	public static final String IDENTIFIER_READY 			= "ready";
	public static final String IDENTIFIER_OUT_ROOM			= "out_room";
	public static final String IDENTIFIER_START				= "start";

	public static final String IDENTIFIER_TEST = "test";
	public static final String IDENTIFIER_ROOM_INFO 		= "room_info";

	public static final String IDENTIFIER_OTHER_ACCEPT		= "other_accept";
	public static final String IDENTIFIER_ROOM_PASSWORD		= "room_password";
	
	
	//다빈치코드 
	public static final String IDENTIFIER_INIT_GAME        	= "init_game";
	public static final String IDENTIFIER_SELECT_NUMBER		= "select_number";
	public static final String IDENTIFIER_TURN				= "turn";
	public static final String IDENTIFIER_GAME_CARD_INFO	= "game_card_info";
	public static final String IDENTIFIER_OPEN_CARD			= "open_card";
	public static final String IDENTIFIER_GAME_FINISH		= "game_finish";
	
	public static Gson gson = new Gson();
	
	public static final int MAX_CARD_COUNT = 24; 
	
	//에러 코드
	public static final int ALREAD_SELECTED_CODE = 1000;
}
