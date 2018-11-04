package com.game.core.codec;


import com.game.core.codec.result.Response;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseEncoder extends MessageToByteEncoder<Response>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
		//写包头
		out.writeInt(ConstantValue.HEADER_FLAG);
		//模块号
		out.writeShort(msg.getModule());
		//写命令号
		out.writeShort(msg.getCmd());
		//写数据长度
		
		int length = msg.getData()==null?0:msg.getData().length;
		if(length<=0){
			out.writeInt(length);
		}else{
			out.writeInt(length);
			out.writeBytes(msg.getData());
		}
	}

}
