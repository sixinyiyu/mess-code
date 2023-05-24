package io.github;

import io.github.producer.AbstractProducer;
import io.github.producer.StdoutProducer;
import io.github.source.AbstractSource;
import io.github.source.DemoSourceWork;
import io.github.work.AliceJobWork;
import io.github.work.Worker;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {

        AbstractProducer producer = new StdoutProducer();

        AbstractSource sourceWork = new DemoSourceWork();

        Worker aliceJobWork = new AliceJobWork(producer, sourceWork);

        try {
            CompletableFuture.runAsync(() -> {
                try {
                    sourceWork.runLoop();
                } catch (Exception e) {
                    //
                }
            });
            aliceJobWork.runLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
