package com.game.core.iosession;

/**
 * 会话接口
 * @author Administrator
 *
 */
public interface IoSession {
	/**
	 * 获取会话绑定对象
	 * @return
	 */
	Object getAttachment();
	/**
	 * 绑定会话对象
	 * @param attachment
	 */
	void setAttachment(Object attachment);
	/**
	 * 移除绑定对象
	 */
	void removeAttachment();
	/**
	 * 向会话中写入消息
	 * @param message
	 */
	void write(Object message);
	/**
	 * 判断会话是否在连接中
	 * @return
	 */
	boolean isConnected();
	/**
	 * 关闭会话
	 */
	void close();
}
