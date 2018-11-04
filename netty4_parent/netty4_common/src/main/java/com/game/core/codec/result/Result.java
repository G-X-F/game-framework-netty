package com.game.core.codec.result;

import com.google.protobuf.GeneratedMessageV3;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T extends GeneratedMessageV3> {
	/**
	 * 结果码
	 */
	private int resultCode;
	/**
	 * 结果内容
	 */
	private T content;
}
