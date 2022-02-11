package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.PagedService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;
import edu.byu.cs.tweeter.client.presenter.View.PagedView;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedPresenter<User> {
    FollowService followService;


    public FollowersPresenter(PagedView<User> view) {
        super(view);
        this.followService = new FollowService();
    }

    @Override
    void getItems(AuthToken authToken, User targetUser, int pageSize, User lastItem) {
        followService.getFollowers(authToken, targetUser, pageSize, lastItem, new GetFollowersObserver());
    }

    @Override
    String getDescription() {
        return "Failed to get user ";
    }

    public class GetFollowersObserver implements PagedObserver<User> {

        @Override
        public void handleSuccess(List<User> followers, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingStatus(false);

            lastItem = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
            setHasMorePages(hasMorePages);

            view.addItems(followers);
        }

        @Override
        public void handleError(String message) {
            isLoading = false;
            view.setLoadingStatus(false);
            view.displayErrorMessage("Failed to get followers " + message);
        }
    }
}
