package com.boardgame.request;

import com.boardgame.common.Common;

public class RequestGameList extends RequestBase {

	public RequestGameList(String identifier, String email) {
		super(identifier, Common.NO_DATA, email);
	}

}
