package com.game.server.scaner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoker {
	/**
	 * 方法
	 */
	private Method method;
	
	/**
	 * 目标对象
	 */
	private Object target;
	
	
	/**
	 * 执行
	 * @param paramValues
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Object invoke(Object... paramValues){
		try {
			return method.invoke(target, paramValues);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
