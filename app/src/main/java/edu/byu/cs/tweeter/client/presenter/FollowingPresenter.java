package edu.byu.cs.tweeter.client.presenter;


import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;
import edu.byu.cs.tweeter.client.presenter.View.PagedView;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends PagedPresenter<User> {
    private FollowService followService;

    public FollowingPresenter(PagedView<User> view) {
        super(view);
        this.followService = new FollowService();
    }

    @Override
    void getItems(AuthToken authToken, User targetUser, int pageSize, User lastItem) {
        followService.getFollowing(authToken, targetUser, pageSize, lastItem, new GetItemsObserver());
    }

    @Override
    String getDescription() {
        return "Failed to get following";
    }
}
