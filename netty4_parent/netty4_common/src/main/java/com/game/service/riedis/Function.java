package com.game.service.riedis;

public interface Function<T,E> {
	T callback(E e);
}
