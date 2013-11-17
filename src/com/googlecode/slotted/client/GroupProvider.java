package com.googlecode.slotted.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.AsyncProvider;
import com.google.gwt.core.client.Callback;

abstract public class GroupProvider implements AsyncProvider<Activity, Throwable> {
    @Override public final void get(Callback<? super Activity, ? super Throwable> callback) {
        throw new UnsupportedOperationException("Slotted should detect GroupProvider and call get(Place, Callback)");
    }

    abstract public void get(SlottedPlace place, Callback<? super Activity, ? super Throwable> callback);
}
