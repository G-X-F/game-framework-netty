package com.game.server.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.game.core.codec.result.Result;
import com.game.core.entities.PlayerModule.Player;
import com.game.core.iosession.IoSession;
import com.game.core.iosession.IoSessionManager;
import com.game.server.handler.PlayerHandler;
import com.game.server.service.PlayerService;
import com.google.protobuf.InvalidProtocolBufferException;

@Component
public class PlayerHandlerImpl implements PlayerHandler {
	
	@Autowired
	private PlayerService playerService;

	@Override
	public Result<Player> registerAndLogin(IoSession iosession, byte[] data) {
		Result<Player> result = new Result<Player>();
		Player player = null;
		try {
			player = Player.parseFrom(data);
			//查看名字是否被占用
			boolean existUserName = playerService.existUserName(player.getName());
			if(existUserName){
				throw new RuntimeException("名字已经被占用");
			}
			//创建新玩家
			playerService.rigister(player);
			
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Result<Player> login(IoSession iosession, byte[] data) {
		//查看当前会话是否已经有人占用
		Object attachment = iosession.getAttachment();
		if(null != attachment){
			throw new RuntimeException("会话已经登录");
		}
		//查询是否在其它地方登录
		try {
			Player player = Player.parseFrom(data);
			boolean onlinePlayer = IoSessionManager.isOnlinePlayer(player.getPlayerId());
			if(onlinePlayer){
				//从在线会话群中移除
				IoSession removeIoSession = IoSessionManager.removeIoSession(player.getPlayerId());
				//清除用户
				removeIoSession.setAttachment(null);
				//关闭channel
				removeIoSession.close();
			}
			//加入新会话
			if(IoSessionManager.putIosession(player.getPlayerId(), iosession)){
				iosession.setAttachment(player);
			}else{
				throw new RuntimeException("加入新会话失败");
			}
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
