package com.game.core.codec.result;

public interface ResultCode {
	/**
	 * 成功
	 */
	int SUCCESS = 0;
	
	/**
	 * 找不到命令
	 */
	int NO_INVOKER = 1;
	
	/**
	 * 参数异常
	 */
	int AGRUMENT_ERROR = 2;
	
	/**
	 * 未知异常
	 */
	int UNKOWN_EXCEPTION = 3;
}
