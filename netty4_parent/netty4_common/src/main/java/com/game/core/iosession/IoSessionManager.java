package com.game.core.iosession;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.GeneratedMessage;

/**
 * 在线会话管理者
 * @author Administrator
 *
 */
public class IoSessionManager {
	/**
	 * 在线会话保持器
	 */
	private final static ConcurrentHashMap<Long, IoSession> iosessions = new ConcurrentHashMap<Long, IoSession>();
	/**
	 * 加入会话
	 * @param palyerId
	 * @param session
	 * @return
	 */
	public static boolean putIosession(Long palyerId,IoSession session){
		if(!iosessions.containsKey(palyerId)){
			boolean success =iosessions.putIfAbsent(palyerId, session)==null?true:false;
			return success;
		}
		return false;
	}
	/**
	 * 移除会话
	 * @param palyerId
	 * @return
	 */
	public static IoSession removeIoSession(Long palyerId){
		return iosessions.remove(palyerId);
	}
	/**
	 * 发送[protoBuf]消息
	 * @param playerId
	 * @param module
	 * @param cmd
	 * @param message
	 */
	public static <T extends GeneratedMessage> void sendMessage(long playerId,short module,short cmd,T message){
		IoSession ioSession = iosessions.get(playerId);
		if(null != ioSession && ioSession.isConnected()){
			/*Response response = new Response(module, cmd, message.toByteArray());
			ioSession.write(response);*/
		}
	}
	/**
	 * 判断是否在线
	 * @param playerId
	 * @return
	 */
	public static boolean isOnlinePlayer(long playerId){
		return iosessions.containsKey(playerId);
	}
	/**
	 * 获取在线的所有玩家
	 * @return
	 */
	public static Set<Long> getOnlinePlayers(){
		return Collections.unmodifiableSet(iosessions.keySet());
	}
}
