package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseService {
    private ExecutorService executor;

    public BaseService() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    protected void executeTask(Runnable task) {
        this.executor.execute(task);
    }
}
