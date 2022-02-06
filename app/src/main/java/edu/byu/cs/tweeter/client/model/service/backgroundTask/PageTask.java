package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PageTask<T> extends AuthorizedTask {

    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";
    private List<T> items;
    private boolean hasMorePages;

    /**
     * Maximum number of followed users to return (i.e., page size).
     */
    private int limit;
    /**
     * The last person being followed returned in the previous page of results (can be null).
     * This allows the new page to begin where the previous page ended.
     */
    private T lastItem;

    public PageTask(AuthToken authToken, Handler messageHandler, int limit, T lastItem) {
        super(authToken, messageHandler);
        this.limit = limit;
        this.lastItem = lastItem;
    }

    @Override
    protected void runTask() {
        Pair<List<T>, Boolean> pageOfUsers = getItems();

        items = pageOfUsers.getFirst();
        hasMorePages = pageOfUsers.getSecond();
    }

    @Override
    protected void loadMessageBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }

    protected abstract Pair<List<T>, Boolean> getItems();

    public int getLimit() {
        return limit;
    }

    public T getLastItem() {
        return lastItem;
    }
}
