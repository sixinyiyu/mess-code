package io.github.work;

import io.github.common.RunState;

public abstract class AbstractRunWork {

    private volatile RunState workState = RunState.RUNNING;

    public boolean isRunning() {
        return workState == RunState.RUNNING;
    }

    public void stopRunning() {
        this.workState = RunState.STOPPED;
    }

    abstract public void beforeStart();

    abstract public void beforeStop();

    abstract public void work() throws Exception;


    public void runLoop() throws Exception {
        this.beforeStart();
        try {
            while (this.isRunning()) {
                work();
            }
        } finally {
            this.beforeStop();
            this.stopRunning();
        }
    }
}
