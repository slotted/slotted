package com.googlecode.slotted.gap_codesplitting.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.googlecode.slotted.client.AsyncActivityMapper;
import com.googlecode.slotted.client.RunActivityCallback;

public class AppActivityMapper implements AsyncActivityMapper {
    private ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override public void getActivityAsync(final Place place, Callback<Activity, Throwable> callback) {
        GWT.runAsync(new RunActivityCallback(callback) {
            @Override public Activity getActivity() {
                return AppActivityMapper.this.getActivity(place);
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