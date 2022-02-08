package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;

public class PagedService extends BaseService {

    public void getUser(String userAlias, SimpleItemObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new GetUserHandler(getUserObserver));
        executeTask(getUserTask);
    }
}
