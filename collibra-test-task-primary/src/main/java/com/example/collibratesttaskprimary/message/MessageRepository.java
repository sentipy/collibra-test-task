package com.example.collibratesttaskprimary.message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MessageRepository<T, R> {

    private final ConcurrentMap<T, R> map = new ConcurrentHashMap<>();

    public Object addMessage(T id, R object) {
        return this.map.put(id, object);
    }

    public R remove(T id) {
        return this.map.remove(id);
    }
}
