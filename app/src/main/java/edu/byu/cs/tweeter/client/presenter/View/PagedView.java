package edu.byu.cs.tweeter.client.presenter.View;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface PagedView<T> extends BaseView{
    abstract void setLoadingStatus(boolean isLoading);

    abstract void addItems(List<T> items);

    abstract void navigateToUser(User user);
}
