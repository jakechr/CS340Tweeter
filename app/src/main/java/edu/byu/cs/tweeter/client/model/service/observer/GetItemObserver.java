package edu.byu.cs.tweeter.client.model.service.observer;

public interface GetItemObserver<T> extends ServiceObserver {
    void handleSuccess(T responseItem);
}
