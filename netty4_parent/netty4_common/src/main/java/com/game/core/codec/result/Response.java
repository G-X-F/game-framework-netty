package com.game.core.codec.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应对象
 * @author Administrator
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
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
	/**
	 * 响应状态码
	 */
	private int stateCode;
}
