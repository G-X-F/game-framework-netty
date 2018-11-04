package com.game.core.codec;

import java.util.List;

import com.game.core.codec.result.Request;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 数据包解码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头	|  模块号      |  命令号    |   长度     |   数据       |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * 包头4字节
 * 模块号2字节 
 * 命令号2字节
 * 长度4字节(数据部分占有字节数量)
 * 
 * @author Administrator
 *
 */
public class RequestDecoder extends ByteToMessageDecoder{
	
	public static int BASE_LENGTH = 4+2+2+4;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		while (true){
			int len = in.readableBytes();
			if(len >= BASE_LENGTH && len < 2048){
				
				//读取包头
				while(true){
					//标记初始读的位置
					in.markReaderIndex();
					//读到了包头就停止
					if(in.readInt()== ConstantValue.HEADER_FLAG){
						break;
					}
					//没有读到包头,重置到初始位置
					in.resetReaderIndex();
					//跳过一个字节
					in.readByte();
				}
				//读取模块号
				short module = in.readShort();
				//读取命令号
				short cmd = in.readShort();
				
				//读取data[]的长度
				int length = in.readInt();
				
				if(length < 0){
					ctx.channel().close();
				}
				//如果数据没有到齐
				if(in.readableBytes() < length){
					//重置到初始位置
					in.resetReaderIndex();
					return;
				}
				
				//如果到齐了，读取数据部分
				byte[] data = new byte[length];
				in.readBytes(data);
				Request msg = new Request(module,cmd,data);
				out.add(msg);
			}else{
				break;
			}
		}
	}

}
