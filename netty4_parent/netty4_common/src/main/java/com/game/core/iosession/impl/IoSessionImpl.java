package com.game.core.iosession.impl;

import com.game.core.iosession.IoSession;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IoSessionImpl implements IoSession{
	
	public static AttributeKey<Object> ATTACHMENT_KEY = AttributeKey.valueOf("ATTACHMENT_KEY");
	
	private Channel channel;
	
	public IoSessionImpl(Channel channel){
		this.channel = channel;
	}

	@Override
	public Object getAttachment() {
		return channel.attr(ATTACHMENT_KEY).get();
	}

	@Override
	public void setAttachment(Object attachment) {
		channel.attr(ATTACHMENT_KEY).set(attachment);
	}

	@Override
	public void removeAttachment() {
		channel.attr(ATTACHMENT_KEY).set(null);
	}

	@Override
	public void write(Object message) {
		channel.writeAndFlush(message);
	}

	@Override
	public boolean isConnected() {
		return channel.isActive();
	}

	@Override
	public void close() {
		channel.close();
	}

}
