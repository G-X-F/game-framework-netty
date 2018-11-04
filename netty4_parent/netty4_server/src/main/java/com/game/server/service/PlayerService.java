package com.game.server.service;

import com.game.core.entities.PlayerModule.Player;

public interface PlayerService {
	/**
	 * 用户注册
	 * @param player
	 * @return
	 */
	public boolean rigister(Player player);
	/**
	 * 登录
	 * @param player
	 * @return
	 */
	public Player login(Player player);
	/**
	 * 查询是否存在某个player
	 * @param playId
	 * @return
	 */
	public boolean existPlayer(long playId);
	/**
	 * 查询某个名字是否被占用
	 * @param username
	 * @return
	 */
	public boolean existUserName(String username);
}
