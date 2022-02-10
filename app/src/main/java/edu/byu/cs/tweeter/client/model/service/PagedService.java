package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Handler.SimpleItemHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class PagedService extends BaseService {

    public void getUser(String userAlias, SimpleItemObserver<User> getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(), userAlias, new SimpleItemHandler(getUserObserver));
        executeTask(getUserTask);
    }
}
