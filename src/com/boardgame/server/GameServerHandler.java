package com.boardgame.server;

import org.apache.commons.codec.binary.Base64;

import com.boardgame.controller.RequestController;
import com.google.gson.Gson;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameServerHandler extends ChannelInboundHandlerAdapter {

	Gson gson = new Gson();
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//접속
		System.out.println("active");		

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		// 클라이언트 메시지 왔을때
		ByteBuf in = (ByteBuf) msg;
		StringBuffer buffer = new StringBuffer();

		try {
			while (in.isReadable()) { // (1)
				buffer.append((char) in.readByte());
			}
			
			byte[] data = buffer.toString().getBytes("UTF-8"); 
			data = Base64.decodeBase64(data);
			String text = new String(data, "UTF-8"); 
			System.out.println("res : " + text );
			RequestController.Instance().reqData(text, ctx);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		//오류났을때
		cause.printStackTrace();
		ctx.close();
		System.out.println("exceptionCaught");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//클라이언트 메시지 완료
		//ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
		System.out.println("complete");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		//클라 접속 종료
		System.out.println("in active");
	}


}
