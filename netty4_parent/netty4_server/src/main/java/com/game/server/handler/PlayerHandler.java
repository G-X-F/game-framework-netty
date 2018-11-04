package com.game.server.handler;

import com.game.core.annotion.SocketCommand;
import com.game.core.annotion.SocketModule;
import com.game.core.codec.result.Result;
import com.game.core.entities.PlayerModule.Player;
import com.game.core.iosession.IoSession;
import com.game.module.ModuleId;
import com.game.module.player.PlayerCmd;

@SocketModule(module=ModuleId.PLAYER)
public interface PlayerHandler {
	/**
	 * 注册并登录
	 * @param iosession
	 * @param data
	 * @return
	 */
	@SocketCommand(cmd=PlayerCmd.REGISTER_AND_LOGIN)
	public Result<Player> registerAndLogin(IoSession iosession,byte[] data);
	/**
	 * 登录
	 * @param playerId
	 * @param data
	 * @return
	 */
	@SocketCommand(cmd=PlayerCmd.LOGIN)
	public Result<Player> login(IoSession iosession,byte[] data);
}
