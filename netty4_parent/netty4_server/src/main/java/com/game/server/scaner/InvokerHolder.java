package com.game.server.scaner;

import java.util.HashMap;
import java.util.Map;

/**
 * 模块、命令、方法存储器
 * @author Administrator
 *
 */
public class InvokerHolder {
	/**
	 * 命令存储器
	 */
    private static Map<Short, Map<Short, Invoker>> invokers = new HashMap<>();
    
    /**
     * 添加命令调用
     * @param module
     * @param cmd
     * @param invoker
     */
    public static void addInvoker(short module, short cmd, Invoker invoker){
    	Map<Short, Invoker> map = invokers.get(module);
    	if(map == null){
    		map = new HashMap<>();
    		invokers.put(module, map);
    	}
    	map.put(cmd, invoker);
    }
    
    
    /**
     * 获取命令调用
     * @param module
     * @param cmd
     * @param invoker
     */
    public static Invoker getInvoker(short module, short cmd){
    	Map<Short, Invoker> map = invokers.get(module);
    	if(map != null){
    		return map.get(cmd);
    	}
    	return null;
    }
}
