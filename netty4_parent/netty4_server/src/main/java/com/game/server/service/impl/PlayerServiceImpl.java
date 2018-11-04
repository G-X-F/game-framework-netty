package com.game.server.service.impl;

import org.springframework.stereotype.Service;

import com.game.core.entities.PlayerModule.Player;
import com.game.server.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService{

	@Override
	public boolean rigister(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Player login(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existPlayer(long playId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existUserName(String username) {
		// TODO Auto-generated method stub
		return false;
	}

}
