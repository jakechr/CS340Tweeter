package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;

public class BaseService {
    private ExecutorService executor;

    public BaseService() {
        this.executor = Executors.newSingleThreadExecutor();
    }

    protected void executeTask(Runnable task) {
        this.executor.execute(task);
    }
}
