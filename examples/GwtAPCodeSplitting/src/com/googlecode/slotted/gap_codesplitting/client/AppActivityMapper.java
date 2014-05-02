package com.googlecode.slotted.gap_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.Place;
import com.googlecode.slotted.client.AsyncActivityMapper;
import com.googlecode.slotted.client.SlottedException;

public class AppActivityMapper implements AsyncActivityMapper {
    private ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override public void getActivityAsync(final Place place, final Callback<Activity, Throwable> callback) {
        GWT.runAsync(new RunAsyncCallback() {
            @Override public void onFailure(Throwable reason) {
                callback.onFailure(reason);
            }

            @Override public void onSuccess() {
                Activity activity = AppActivityMapper.this.getActivity(place);
                if (activity != null) {
                    callback.onSuccess(activity);
                } else {
                    callback.onFailure(new SlottedException(place.getClass().getName() + " is not found."));
                }
            }
        });
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof HelloPlace)
            return new HelloActivity((HelloPlace) place, clientFactory);
        else if (place instanceof GoodbyePlace)
            return new GoodbyeActivity((GoodbyePlace) place, clientFactory);
        return null;
    }
}