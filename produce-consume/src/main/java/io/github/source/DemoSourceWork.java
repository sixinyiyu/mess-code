package io.github.source;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import io.github.common.Event;
import io.github.work.Worker;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class DemoSourceWork extends AbstractSource implements Worker {

    private final AtomicBoolean stopState = new AtomicBoolean(false);

    private final List<AbstractSource.EventListener> eventListeners = new CopyOnWriteArrayList<AbstractSource.EventListener>();

    public void stop() {
        this.stopState.set(true);
    }

    public void start() {
        this.stopState.set(false);
    }

    @Override
    public void runLoop() throws Exception {
        while (true) {
            if (this.stopState.get()) {
                break;
            }
            notifyEventListeners(mockData());
            quiteSleep(500);
        }
    }

    private void quiteSleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (Exception e) {
            //ignore
        }
    }

    private void notifyEventListeners(Event event) {
        if (null != event) {
            for (AbstractSource.EventListener listener: eventListeners) {
                try {
                    listener.onEvent(event);
                } catch (Exception e) {
                    //ignore
                }
            }
        }
    }

    private Event mockData() {
        Event event = new Event();
        event.setId(NanoIdUtils.randomNanoId());
        event.setContent("工作10秒");
        return event;
    }


    @Override
    public void registerListener(AbstractSource.EventListener listener) {
        eventListeners.add(listener);
    }
}
