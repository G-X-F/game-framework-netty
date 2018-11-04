package com.game.core.codec.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息对象
 * @author Administrator
 *
 */

@Data
@AllArgsConstructor
public class Request {
	/**
	 * 模块号
	 */
	private short module;
	/**
	 * 命令号
	 */
	private short cmd;
	/**
	 * 二进制数据
	 */
	private byte[] data;
}
