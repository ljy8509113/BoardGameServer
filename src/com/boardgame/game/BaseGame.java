package com.boardgame.game;

import com.boardgame.model.GameRoom;

import io.netty.channel.ChannelHandlerContext;

public abstract class BaseGame {
	public GameRoom room;
	public abstract void setData(String identifier, String json, ChannelHandlerContext ctx);
	public abstract void startGame();
}
