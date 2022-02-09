package edu.byu.cs.tweeter.client.model.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Handler.PagedHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Handler.SimpleItemHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.Handler.SimpleNotificationHandler;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleNotificationObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService extends PagedService {


    public void getFollowing(AuthToken currUserAuthToken, User user, int pageSize, User lastFollowee, PagedObserver<User> getFollowingObserver) {
        GetFollowingTask getFollowingTask = new GetFollowingTask(currUserAuthToken,
                user, pageSize, lastFollowee, new PagedHandler<>(getFollowingObserver));
        executeTask(getFollowingTask);
    }

    public void getFollowers(AuthToken currUserAuthToken, User user, int pageSize, User lastFollower, PagedObserver<User> getFollowersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(currUserAuthToken,
                user, pageSize, lastFollower, new PagedHandler<>(getFollowersObserver));
        executeTask(getFollowersTask);
    }

    public void unfollow(AuthToken currUserAuthToken, User selectedUser, SimpleNotificationObserver unfollowObserver) {
        UnfollowTask unfollowTask = new UnfollowTask(currUserAuthToken,
                selectedUser, new SimpleNotificationHandler(unfollowObserver));
       executeTask(unfollowTask);
    }

    public void follow(AuthToken currUserAuthToken, User selectedUser, SimpleNotificationObserver followObserver) {
        FollowTask followTask = new FollowTask(currUserAuthToken,
                selectedUser, new SimpleNotificationHandler(followObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(followTask);
    }


    public void isFollower(AuthToken currUserAuthToken, User selectedUser, User currUser, SimpleItemObserver<Boolean> isFollowerObserver) {
        IsFollowerTask isFollowerTask = new IsFollowerTask(currUserAuthToken,
               currUser, selectedUser, new SimpleItemHandler<>(isFollowerObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(isFollowerTask);
    }

    public void getFollowersCount(AuthToken currUserAuthToken, User selectedUser, SimpleItemObserver<Integer> getFollowersCountObserver) {
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(currUserAuthToken,
                selectedUser, new SimpleItemHandler<>(getFollowersCountObserver));
        executeTask(followersCountTask);
    }


    public void getFollowingCount(AuthToken currUserAuthToken, User selectedUser, SimpleItemObserver<Integer> getFollowingCountObserver) {
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(currUserAuthToken,
                selectedUser, new SimpleItemHandler<>(getFollowingCountObserver));
        executeTask(followingCountTask);
    }

}
