package com.example.collibratesttaskprimary.processor;

public interface PromotionMessagePrimaryProcessor<T> {

    void process(T message) throws Exception;
}
