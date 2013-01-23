package com.googlecode.slotted.gap_example.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {
    private ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
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