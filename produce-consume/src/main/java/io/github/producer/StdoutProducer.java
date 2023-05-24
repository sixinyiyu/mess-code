package io.github.producer;

import io.github.common.DataMess;

public class StdoutProducer extends AbstractProducer {

    @Override
    public void push(DataMess data) throws Exception {
        System.out.println("收到数据:" + data.getBody());
    }
}
