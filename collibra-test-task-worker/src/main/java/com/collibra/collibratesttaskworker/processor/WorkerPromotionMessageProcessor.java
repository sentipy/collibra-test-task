package com.collibra.collibratesttaskworker.processor;

public interface WorkerPromotionMessageProcessor<T> {

    void process(T message);
}
