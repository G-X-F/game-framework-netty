package com.game.server.scaner;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.game.core.annotion.SocketCommand;
import com.game.core.annotion.SocketModule;

/**
 * handler 扫描器
 * @author Administrator
 *
 */
@Component
public class HandlerScanner implements BeanPostProcessor{

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Class<? extends Object> clazz = bean.getClass();
		Class<?>[] interfaces = clazz.getInterfaces();
		if(interfaces!=null && interfaces.length>0){
			//扫描类的所有接口父类
			for (Class<?> interFace : interfaces) {
				SocketModule socketModule = interFace.getAnnotation(SocketModule.class);
				if(socketModule == null){
					continue;
				}
				//找出命令方法
				Method[] methods = interFace.getMethods();
				if(methods != null && methods.length > 0){
					for (Method method : methods) {
						SocketCommand socketCommand = method.getAnnotation(SocketCommand.class);
						if(socketCommand == null){
							continue;
						}
						final short module = socketModule.module();
						final short cmd = socketCommand.cmd();
						
						//将方法进行存储
						if(InvokerHolder.getInvoker(module, cmd)== null){
							InvokerHolder.addInvoker(module, cmd, new Invoker(method, bean));
						}
					}
				}
			}
		}
		return bean;
	}

}
