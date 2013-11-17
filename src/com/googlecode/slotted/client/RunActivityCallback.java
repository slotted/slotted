package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.RunAsyncCallback;

abstract public class RunActivityCallback implements RunAsyncCallback {
    private Callback<Activity, Throwable> callback;

    abstract public Activity getActivity();

    @Override public void onFailure(Throwable reason) {
        throw new RuntimeException("Code Splitting Load Failure", reason);
    }

    @Override public void onSuccess() {
        callback.onSuccess(getActivity());
    }
}
