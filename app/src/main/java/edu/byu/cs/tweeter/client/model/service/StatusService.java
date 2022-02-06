package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService extends BaseService{

    public interface GetFeedObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);
        void handleError(String message);
    }

    public interface GetStoryObserver {
        void handleSuccess(List<Status> statuses, boolean hasMorePages);
        void handleError(String message);
    }

    public interface PostStatusObserver {
        void handleSuccess();
        void handleError(String message);
    }

    public void loadMoreItems(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus, GetFeedObserver getFeedObserver) {
        GetFeedTask getFeedTask = new GetFeedTask(currUserAuthToken,
                user, pageSize, lastStatus, new GetFeedHandler(getFeedObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getFeedTask);
    }

    public void loadMoreStoryItems(AuthToken currUserAuthToken, User user, int pageSize, Status lastStatus, GetStoryObserver getStoryObserver) {
        GetStoryTask getStoryTask = new GetStoryTask(currUserAuthToken,
                user, pageSize, lastStatus, new GetStoryHandler(getStoryObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(getStoryTask);
    }

    /**
     * Message handler (i.e., observer) for GetFeedTask.
     */
    private class GetFeedHandler extends Handler {
        GetFeedObserver observer;

        public GetFeedHandler(GetFeedObserver getFeedObserver) {
            this.observer = getFeedObserver;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            boolean success = msg.getData().getBoolean(GetFeedTask.SUCCESS_KEY);
            if (success) {
                List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetFeedTask.ITEMS_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
                observer.handleSuccess(statuses, hasMorePages);
            } else if (msg.getData().containsKey(GetFeedTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetFeedTask.MESSAGE_KEY);
                observer.handleError("Failed to get feed: " + message);
            } else if (msg.getData().containsKey(GetFeedTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetFeedTask.EXCEPTION_KEY);
                observer.handleError("Failed to get feed because of exception: " + ex.getMessage());
            }
        }
    }


    /**
     * Message handler (i.e., observer) for GetStoryTask.
     */
    private class GetStoryHandler extends Handler {
        GetStoryObserver observer;

        GetStoryHandler(GetStoryObserver observer) {
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {

            boolean success = msg.getData().getBoolean(GetStoryTask.SUCCESS_KEY);
            if (success) {
                List<Status> statuses = (List<Status>) msg.getData().getSerializable(GetStoryTask.ITEMS_KEY);
                boolean hasMorePages = msg.getData().getBoolean(GetStoryTask.MORE_PAGES_KEY);
                observer.handleSuccess(statuses, hasMorePages);
            } else if (msg.getData().containsKey(GetStoryTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(GetStoryTask.MESSAGE_KEY);
                observer.handleError("Failed to get story: " + message);
            } else if (msg.getData().containsKey(GetStoryTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(GetStoryTask.EXCEPTION_KEY);
                observer.handleError("Failed to get story because of exception: " + ex.getMessage());
            }
        }
    }

    public void postStatus(AuthToken currUserAuthToken, Status newStatus, MainPresenter.PostStatusObserver postStatusObserver) {
        PostStatusTask statusTask = new PostStatusTask(currUserAuthToken,
                newStatus, new PostStatusHandler(postStatusObserver));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(statusTask);
    }

    // PostStatusHandler

    private class PostStatusHandler extends Handler {
        PostStatusObserver observer;

        public PostStatusHandler(PostStatusObserver observer) {
            this.observer = observer;
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            boolean success = msg.getData().getBoolean(PostStatusTask.SUCCESS_KEY);
            if (success) {
                observer.handleSuccess();
            } else if (msg.getData().containsKey(PostStatusTask.MESSAGE_KEY)) {
                String message = msg.getData().getString(PostStatusTask.MESSAGE_KEY);
                observer.handleError("Failed to post status: " + message);
            } else if (msg.getData().containsKey(PostStatusTask.EXCEPTION_KEY)) {
                Exception ex = (Exception) msg.getData().getSerializable(PostStatusTask.EXCEPTION_KEY);
                observer.handleError("Failed to post status because of exception: " + ex.getMessage());
            }
        }
    }
}
