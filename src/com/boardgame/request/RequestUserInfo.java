package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestUserInfo extends RequestBase {
	
	public RequestUserInfo(Integer gameNo, String uuid) {
		super(Common.IDENTIFIER_USER_INFO, gameNo, uuid);
	}
}
