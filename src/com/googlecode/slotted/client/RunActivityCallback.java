package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.RunAsyncCallback;

abstract public class RunActivityCallback implements RunAsyncCallback {
    private Callback<? super Activity, ? super Throwable> callback;

    protected RunActivityCallback(Callback<? super Activity, ? super Throwable> callback) {
        this.callback = callback;
    }

    abstract public Activity getActivity();

    @Override public void onFailure(Throwable reason) {
        throw new RuntimeException("Code Splitting Load Failure", reason);
    }

    @Override public void onSuccess() {
        callback.onSuccess(getActivity());
    }
}
