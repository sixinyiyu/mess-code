package io.github.work;

import io.github.common.DataMess;
import io.github.common.Event;
import io.github.producer.AbstractProducer;
import io.github.source.AbstractSource;
import io.github.source.SourceWorkEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class AliceJobWork extends AbstractRunWork implements Worker {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AbstractSource sourceWorker;

    private final AbstractProducer producer;

    private final AbstractSource.EventListener dataWorkEventListener;

    private final LinkedBlockingDeque<Event> queue;

    public AliceJobWork(AbstractProducer producer, AbstractSource source) {

        this.producer = producer;
        this.sourceWorker = source;
        this.queue = new LinkedBlockingDeque<Event>(5000);
        // 开始模拟产生数据
//        this.dataWork.
        dataWorkEventListener = new SourceWorkEventListener(this.sourceWorker, this.queue);
        // 注册数据产生事件监听处理器
        this.sourceWorker.registerListener(dataWorkEventListener);
    }

    private Event pullEvent() throws InterruptedException {
        return this.queue.poll(100, TimeUnit.MILLISECONDS);
    }

    protected DataMess getOneDataMess() {
        DataMess mess = null;
        while (true) {
            try {
                Event event = pullEvent();
                if (Objects.nonNull(event)) {
                    //
                    mess = new DataMess(event.getId() + "@" + event.getContent());
                }
                break;
            } catch (Exception e) {
            }
        }
        return mess;
    }

    @Override
    public void beforeStart() {
        logger.info("开始上班摸鱼.........");
    }

    @Override
    public void beforeStop() {
        logger.info("下班结束摸鱼.........");
    }

    @Override
    public void work() throws Exception {
        DataMess oneData = getOneDataMess();
        if (Objects.isNull(oneData)) {
            return;
        }
        producer.push(oneData);
    }
}
