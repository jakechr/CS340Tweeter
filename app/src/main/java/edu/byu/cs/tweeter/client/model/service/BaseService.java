package edu.byu.cs.tweeter.client.model.service;

import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.observer.GetItemObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class BaseService {

    public void getUser(String userAlias, GetItemObserver getUserObserver) {
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new GetUserHandler(getUserObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getUserTask);
    }

    /**
     * Message handler (i.e., observer) for GetUserTask.
     */
    private class GetUserHandler extends BackgroundTaskHandler<GetItemObserver<User>> {

        public GetUserHandler(GetItemObserver observer) {
            super(observer);
        }

        @Override
        protected void handleSuccessMessage(GetItemObserver observer, Bundle data) {
            User user = (User) data.getSerializable(GetUserTask.USER_KEY);
            observer.handleSuccess(user);
        }
    }
}
