package com.boardgame.common;

public class Common {
	public static final int GAME_DAVINCICODE = 1;

	public static final String IDENTIFIER_CONNECT_ROOM = "connection_room";
	public static final String IDENTIFIER_ROOM_LIST = "game_room_list";
	public static final String IDENTIFIER_CREATE_ROOM = "create_room";
	public static final String IDENTIFIER_GAMING_USER = "gaming_user";
	public static final String IDENTIFIER_LOGIN = "login";
	public static final String IDENTIFIER_USER_INFO = "user_info";
	public static final String IDENTIFIER_JOIN = "join";
	
	public static final String IDENTIFIER_TEST = "test";
	
	public static final int CODE_SUCCESS 					= 0;
	public static final int CODE_ERROR_DB 					= 100;
	public static final int CODE_ERROR_FULL 				= 101;
	public static final int CODE_ERROR_CONNECTION_ROOM 		= 102;
	public static final int CODE_ERROR_DECRYPTION 			= 103;
	public static final int CODE_ERROR_EMAIL_OVERLAP 		= 200;
	public static final int CODE_ERROR_EMAIL_NOT_FOUND 		= 201;
	public static final int CODE_ERROR_PASSWORD_MISS 		= 202;
	public static final int CODE_ERROR_PASSWORD_FAIL_FULL 	= 203;
	public static final int CODE_ERROR_NICKNAME_OVERLAP 	= 204;
}
