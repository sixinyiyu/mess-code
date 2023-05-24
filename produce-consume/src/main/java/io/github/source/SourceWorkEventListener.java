package io.github.source;

import io.github.common.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class SourceWorkEventListener implements AbstractSource.EventListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final BlockingQueue<Event> queue;

    private final AbstractSource sourceWorker;

    public SourceWorkEventListener(AbstractSource worker, BlockingQueue<Event> queue) {
        this.sourceWorker = worker;
        this.queue = queue;
    }


    @Override
    public void onEvent(Event event) {
        try {
            logger.info("收到原始工作数据.....{}" , event.getId());
            this.queue.offer(event, 100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
