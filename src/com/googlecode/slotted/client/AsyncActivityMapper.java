package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.Callback;
import com.google.gwt.place.shared.Place;

abstract public interface AsyncActivityMapper extends ActivityMapper {
    abstract public void getActivityAsync(Place place, Callback<Activity, Throwable> callback);
}
