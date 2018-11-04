package com.game.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.game.core.codec.RequestDecoder;
import com.game.core.codec.ResponseEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class GameServer {
	public static Logger logger = LoggerFactory.getLogger(GameServer.class); 
	
	public void start(){
		//服务主启动类
		ServerBootstrap strap = new ServerBootstrap();
		
		//创建boss和worker
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		
		try{
			//设置循环线程组事例
			strap.group(boss, worker);
			
			//设置channel管道工厂
			strap.channel(NioServerSocketChannel.class);
			
			//设置管道过滤器
			strap.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//请求解码器
					ch.pipeline().addLast(new RequestDecoder());
					//反馈编码器
					ch.pipeline().addLast(new ResponseEncoder());
					//添加消息处理器
					ch.pipeline().addLast(new ServerHandler());
				}
			});
			
			//设置连接缓冲池队列大小
			strap.option(ChannelOption.SO_BACKLOG, 2048);
			strap.bind(10000).sync();
			logger.info("GameServer_10000 start");
		}catch (Exception e) {
			logger.info("GameServer_10000 start faild");
		}
	}
}
