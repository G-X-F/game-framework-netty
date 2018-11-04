package com.game.server;

import com.game.core.codec.result.Request;
import com.game.core.codec.result.Response;
import com.game.core.codec.result.Result;
import com.game.core.entities.PlayerModule.Player;
import com.game.core.iosession.IoSession;
import com.game.core.iosession.IoSessionManager;
import com.game.core.iosession.impl.IoSessionImpl;
import com.game.module.ModuleId;
import com.game.server.scaner.Invoker;
import com.game.server.scaner.InvokerHolder;
import com.google.protobuf.GeneratedMessageV3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Request>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request msg) throws Exception {
		processMessages(new IoSessionImpl(ctx.channel()), msg);
	}
	
	/**
	 * 消息处理
	 * @param iosession 会话session,保存了实际会话channel
	 * @param request 接收到的消息
	 */
	private void processMessages(IoSession iosession,Request request){
		Response response = new Response();
		Result<?> result = null;
		//获取命令执行器
		Invoker invoker = InvokerHolder.getInvoker(request.getModule(), request.getCmd());
		if(invoker == null){
			throw new RuntimeException("no such invoker");
		}
		if(request.getModule()==ModuleId.PLAYER){
			//假设请求的是玩家模块，可以具体到玩家登录或者创建账号等
			result = (Result<?>) invoker.invoke(iosession,request.getData());
			
		}else{
			//如果是请求其它模块，要先判断是否登录
			Object attachment = iosession.getAttachment();
			if(null == attachment){
				//会话未登录，拒绝请求
				iosession.write("请先登录");
				return;
			}else{
				Player player = (Player)attachment;
				result = (Result<?>)invoker.invoke(player.getPlayerId(),request.getData());
			}
		}
		//回写数据
		if(result.getResultCode()==200){
			Object content = result.getContent();
			if(null != content){
				if(content instanceof GeneratedMessageV3){
					GeneratedMessageV3 msg = (GeneratedMessageV3)content;
					response.setData(msg.toByteArray());
				}
			}
			iosession.write(response);
		}else{
			//写请求失败的消息
		}
		
	}
	/**
	 * 断线移除会话
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		IoSession session = new IoSessionImpl(ctx.channel());
		Object attachment = session.getAttachment();
		if(null != attachment){
			Player player = (Player)attachment;
			IoSessionManager.removeIoSession(player.getPlayerId());
		}
	}
	
	

}
