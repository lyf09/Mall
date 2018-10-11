package com.taotao.commons.service;

public interface Function<T,E> {
    public T callback(E e);
}
