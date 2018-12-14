package com.boardgame.controller;

import com.boardgame.common.Common;
import com.boardgame.request.RequestBase;
import com.boardgame.response.ResponseBase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class RequestController {
	private static RequestController instance= null;

	public static RequestController Instance() {
		if(instance == null) {
			instance = new RequestController();
		}
		return instance;
	}	

	public synchronized void reqData(String result, ChannelHandlerContext ctx) {
		RequestBase header = Common.gson.fromJson(result, RequestBase.class);
		String identifier = header.getIdentifier();
		int gameNo = header.getGameNo();

		switch(gameNo) {
		case Common.GAME_DAVINCICODE:
			DavinciCodeController.Instance().reqData(result, identifier, ctx);
			break;
		}
	}

	public void response(ResponseBase res, ChannelHandlerContext ctx) {
		String resStr = Common.gson.toJson(res);//getJson(res);
//		byte [] mesBytes = resStr.getBytes();
//		
//		short size = (short)mesBytes.length;
//		
//		byte[] sizeBytes = new byte[2];
//		
//		for(int i=0; i<sizeBytes.length && i<Short.SIZE; i++) {
//			sizeBytes[i] = (byte)((size >> (8*i)) & 0xff);
//		}
//		
//		int resSize = sizeBytes.length + mesBytes.length;
//		byte [] resBytes = new byte[resSize];
//		
//		System.arraycopy(sizeBytes, 0, resBytes, 0, sizeBytes.length);
//		System.arraycopy(mesBytes, 0, resBytes, sizeBytes.length, mesBytes.length);
//		ByteBuf buf = Unpooled.wrappedBuffer(resBytes);
//		
		System.out.println( "res : " + resStr );
		ByteBuf buf = Unpooled.copiedBuffer(resStr, CharsetUtil.UTF_8);
		ctx.write(buf);
		ctx.flush();		
	}

}
