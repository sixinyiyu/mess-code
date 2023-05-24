package io.github.source;

import io.github.common.Event;
import io.github.work.Worker;

public abstract class AbstractSource implements Worker {

    abstract public void registerListener(AbstractSource.EventListener listener);

    public interface EventListener {

        void onEvent(Event event);
    }
}
