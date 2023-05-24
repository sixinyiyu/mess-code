package io.github.producer;

import io.github.common.DataMess;

public abstract class AbstractProducer {

    abstract public void push(DataMess data) throws Exception;
}
