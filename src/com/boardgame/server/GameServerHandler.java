package com.boardgame.server;

import com.boardgame.common.Common;
import com.boardgame.controller.DavinciCodeController;
import com.boardgame.controller.GameController;
import com.boardgame.request.RequestBase;
import com.boardgame.response.ResponseBase;
import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameServerHandler extends ChannelInboundHandlerAdapter {

	Gson gson = new Gson();
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//접속
		System.out.println("active");		
		ctx.write("connection success 1");
		
//		GameController.Instance().getRoomList(gameNo);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		// 클라이언트 메시지 왔을때
		ByteBuf in = (ByteBuf) msg;
		StringBuffer buffer = new StringBuffer();
		
	    try {
	        while (in.isReadable()) { // (1)
//	            System.out.print((char) in.readByte());
//	            System.out.flush();
	        	 buffer.append((char) in.readByte());
	        }
//	        ctx.write("server");
//	        ctx.flush();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }finally {
	    	
	    }
		
	    GameController.Instance().reqData(buffer, ctx);
	    
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		//오류났을때
		cause.printStackTrace();
		ctx.close();
	}
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//클라이언트 메시지 완료
		System.out.println("complete");
		ctx.write("connection success 2");
		ctx.flush();
    }
	
	@Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //클라 접속 종료
		System.out.println("in active");
    }
	

}
