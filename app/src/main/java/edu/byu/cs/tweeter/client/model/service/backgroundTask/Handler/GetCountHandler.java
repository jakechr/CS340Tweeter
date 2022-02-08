package edu.byu.cs.tweeter.client.model.service.backgroundTask.Handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetCountTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.observer.SimpleItemObserver;

public class GetCountHandler extends BackgroundTaskHandler<SimpleItemObserver<Integer>> {

    public GetCountHandler(SimpleItemObserver<Integer> observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(SimpleItemObserver<Integer> observer, Bundle data) {
        int count = data.getInt(GetCountTask.COUNT_KEY);
        observer.handleSuccess(count);
    }

}
